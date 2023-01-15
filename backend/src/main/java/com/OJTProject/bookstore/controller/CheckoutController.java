package com.OJTProject.bookstore.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.dto.BillAddressDto;
import com.OJTProject.bookstore.dto.CheckoutGetList;
import com.OJTProject.bookstore.dto.CheckoutInfoDto;
import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.entity.UserTransaction;
import com.OJTProject.bookstore.service.BillingAddressService;
import com.OJTProject.bookstore.service.OrderService;
import com.OJTProject.bookstore.service.PaymentService;
import com.OJTProject.bookstore.service.ShippingAddressService;
import com.OJTProject.bookstore.service.UserCartItemService;
import com.OJTProject.bookstore.service.UserCartService;
import com.OJTProject.bookstore.service.UserPaymentMethodService;
import com.OJTProject.bookstore.service.UserService;
import com.OJTProject.bookstore.service.UserShippingService;
import com.OJTProject.bookstore.utility.MailConstructor;
import com.OJTProject.bookstore.utility.USConstants;

@RestController
@RequestMapping("/api")
public class CheckoutController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserCartItemService userCartItemService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	@Autowired
	private BillingAddressService billingAddressService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserPaymentMethodService userPaymentMethodService;

	@Autowired
	private UserShippingService userShippingService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserCartService userCartService;

	@GetMapping("/checkout")
	private ResponseEntity<?> getCheckout(@RequestParam("userCartId") String userCartId,
			@RequestParam("userId") Long userId) {

		User user = userService.findById(userId);

		if (user.getUserCart().getId() != Long.parseLong(userCartId)) {
			return ResponseEntity.badRequest().body("Cart Id Is not Match");
		}

		List<UserCartItem> userCartItemList = userCartItemService.findByUserCart(user.getUserCart());

		if (userCartItemList.size() == 0) {
			return ResponseEntity.notFound().build();
		}

		for (UserCartItem cartItem : userCartItemList) {
			if (cartItem.getBook().getInStockNumber() < cartItem.getQty()) {
				return ResponseEntity.badRequest().body("Not Enough Stock");
			}
		}

		List<UserShipping> userShippingList = user.getUserShippingList();

		List<UserPaymentMethod> userPaymentMethodList = user.getUserPaymentMethodList();

		List<UserOrderAddress> userOrderAddressList = new ArrayList<>();

		UserTransaction userTransaction = new UserTransaction();

		for (UserShipping userShipping : userShippingList) {
			if (user.getId() == userShipping.getUser().getId()) {
				if (userShipping.getUserShippingDefault()) {
					UserOrderAddress userOrderAddress = new UserOrderAddress();
					shippingAddressService.setByUserShipping(userShipping, userOrderAddress);
					userOrderAddressList.add(userOrderAddress);
				}
			}
		}

		for (UserPaymentMethod userPayment : userPaymentMethodList) {
			if (user.getId() == userPayment.getUser().getId()) {
				if (userPayment.getDefaultPayment()) {
					paymentService.setByUserPayment(userPayment, userTransaction);
					UserOrderAddress userOrderAddress = new UserOrderAddress();
					billingAddressService.setByUserBilling(userPayment.getUserBilling(), userOrderAddress);
					userOrderAddressList.add(userOrderAddress);
				}
			}
		}

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);

		UserCart userCart = user.getUserCart();

		CheckoutGetList checkoutGetList = new CheckoutGetList();

		checkoutGetList.setUserCartItemList(userCartItemList);
		checkoutGetList.setUserShippingList(userShippingList);
		checkoutGetList.setUserPaymentMethodList(userPaymentMethodList);
		checkoutGetList.setUserOrderAddressList(userOrderAddressList);
		checkoutGetList.setUserTransaction(userTransaction);
		checkoutGetList.setStateList(stateList);
		checkoutGetList.setUserCart(userCart);

		return ResponseEntity.ok(checkoutGetList);
	}

	@GetMapping("/payment/update")
	private ResponseEntity<?> setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId,
			@RequestParam("userId") Long userId) {

		UserPaymentMethod userPaymentMethod = userPaymentMethodService.findById(userPaymentId);

		if (!userPaymentMethod.getUser().getId().equals(userId)) {
			return ResponseEntity.badRequest().body("User ID is not match!");
		}

		UserTransaction userTransaction = new UserTransaction();
		UserOrderAddress userOrderAddress = new UserOrderAddress();

		UserTransaction setUserTransaction = paymentService.setByUserPayment(userPaymentMethod, userTransaction);
		UserOrderAddress setBillingAddress = billingAddressService.setByUserBilling(userPaymentMethod.getUserBilling(),
				userOrderAddress);

		BillAddressDto billAddressDto = new BillAddressDto();
		billAddressDto.setUserTransaction(setUserTransaction);
		billAddressDto.setUserOrderAddress(setBillingAddress);

		return ResponseEntity.ok().body(billAddressDto);

	}

	@GetMapping("/shipping/update")
	private ResponseEntity<?> setShippingAddress(@RequestParam("userShippingId") Long userShippingId,
			@RequestParam("userId") Long userId) {

		UserShipping userShipping = userShippingService.findById(userShippingId);

		if (!userShipping.getUser().getId().equals(userId)) {
			return ResponseEntity.badRequest().body("User ID is not match!");
		}

		UserOrderAddress userOrderAddress = new UserOrderAddress();

		UserOrderAddress setShippingAddress = shippingAddressService.setByUserShipping(userShipping, userOrderAddress);

		return ResponseEntity.ok().body(setShippingAddress);
	}

	@PostMapping("/checkout")
	private ResponseEntity<?> postCheckout(@RequestBody CheckoutInfoDto checkoutInfoDto,
			@RequestParam("shippingMethod") String shippingMethod, @RequestParam("userId") Long userId) {

		User user = userService.findById(userId);

		UserCart userCart = user.getUserCart();

		List<UserOrderAddress> userOrderAddressList = new ArrayList<>();

		for (UserOrderAddress userOrderAddress : checkoutInfoDto.getUserOrderAddressList()) {
			UserOrderAddress userOrderAddress2 = new UserOrderAddress();
			userOrderAddress2.setAddressName(userOrderAddress.getAddressName());
			userOrderAddress2.setAddressStreet1(userOrderAddress.getAddressStreet1());
			userOrderAddress2.setAddressStreet2(userOrderAddress.getAddressStreet2());
			userOrderAddress2.setAddressCity(userOrderAddress.getAddressCity());
			userOrderAddress2.setAddressState(userOrderAddress.getAddressState());
			userOrderAddress2.setAddressCountry("US");
			userOrderAddress2.setAddressZipcode(userOrderAddress.getAddressZipcode());
			userOrderAddress2.setShippingBill(userOrderAddress.getShippingBill());
			userOrderAddressList.add(userOrderAddress2);
		} 

		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setCardName(checkoutInfoDto.getUserTransaction().getCardName());
		userTransaction.setCardNumber(checkoutInfoDto.getUserTransaction().getCardNumber());
		userTransaction.setCardType(checkoutInfoDto.getUserTransaction().getCardType());
		userTransaction.setCvc(checkoutInfoDto.getUserTransaction().getCvc());
		userTransaction.setExpiryMonth(checkoutInfoDto.getUserTransaction().getExpiryMonth());
		userTransaction.setExpiryYear(checkoutInfoDto.getUserTransaction().getExpiryYear());
		userTransaction.setHolderName(checkoutInfoDto.getUserTransaction().getHolderName());

		Order order = orderService.createOrder(userCart, userOrderAddressList, userTransaction, shippingMethod, user);

		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));

		userCartService.clearUserCart(userCart);

		LocalDate today = LocalDate.now();

		LocalDate estimatedDeliveryDate;

		if (shippingMethod.equals("groundShipping")) {
			estimatedDeliveryDate = today.plusDays(5);
		} else {
			estimatedDeliveryDate = today.plusDays(3);
		}

		return ResponseEntity.ok().body(estimatedDeliveryDate);
	}

}
