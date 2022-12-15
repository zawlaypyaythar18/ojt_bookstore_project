package com.OJTProject.bookstore.utility;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.OJTProject.bookstore.entity.User;

@Component
public class MailConstructor {

	@Autowired
	private Environment env;
	
	public SimpleMailMessage constructResetTokenEmail(String contextPath,Locale locale,String token,User user, String password,String subject) {
		
		String url = contextPath + "/newUser?token=" + token;
		String message = "\nPlease click on this link to verify email and edit your personal information.Your Password is :\n" + password;
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		email.setSubject(subject);
		email.setText(url + message);
		
		return email;
		
	}
	
}
