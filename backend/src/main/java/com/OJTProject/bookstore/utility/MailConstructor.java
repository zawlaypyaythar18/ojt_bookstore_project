package com.OJTProject.bookstore.utility;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.ShippingBill;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserOrderAddress;

@Component
public class MailConstructor {

	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;

	public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user,
			String password, String subject) {

		String url = contextPath + "/newUser?token=" + token;
		String message = "\nPlease click on this link to verify email and edit your personal information.Your Password is :\n"
				+ password;

		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		email.setSubject(subject);
		email.setText(url + message);

		return email;

	}

	public MimeMessagePreparator constructOrderConfirmationEmail(User user, Order order, Locale locale) {
		Context context = new Context();
		context.setVariable("user", user);

		for (UserOrderAddress orderAddress : order.getUserOrderAddressList()) {
			if (orderAddress.getShippingBill().equals(ShippingBill.shipping)) {
				context.setVariable("shippingAddress", orderAddress);
			} else {
				context.setVariable("billingAddress", orderAddress);
			}
		}

		context.setVariable("order", order);
		context.setVariable("cartItemList", order.getCartItemList());

		String text = templateEngine.process("orderConfirmationEmailTemplate", context);

		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(user.getEmail());
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
				email.setSubject("Order Confirmation - " + order.getId());
				email.setText(text, true);

			}
		};
		return mimeMessagePreparator;
	}

}
