package com.OJTProject.bookstore.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.dto.OrderDetailsDto;
import com.OJTProject.bookstore.dto.StatePaymentList;
import com.OJTProject.bookstore.dto.StateShippingList;
import com.OJTProject.bookstore.dto.UserBillingPayment;
import com.OJTProject.bookstore.entity.LoginRequest;
import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.PasswordResetToken;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserRole;
import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.entity.UserStatus;
import com.OJTProject.bookstore.service.OrderService;
import com.OJTProject.bookstore.service.UserAddressService;
import com.OJTProject.bookstore.service.UserPaymentMethodService;
import com.OJTProject.bookstore.service.UserService;
import com.OJTProject.bookstore.service.UserShippingService;
import com.OJTProject.bookstore.utility.MailConstructor;
import com.OJTProject.bookstore.utility.SecurityUtility;
import com.OJTProject.bookstore.utility.USConstants;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserShippingService userShippingService;

	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	private UserPaymentMethodService userPaymentMethodService;

	@Autowired
	private OrderService orderService;

	@PostMapping("/register")
	private ResponseEntity<?> register(@Valid @RequestBody User user, HttpServletRequest request) throws IOException {

		if (userService.findByEmail(user.getEmail()) != null) {
			return ResponseEntity.badRequest().body("Email is already Exist");
		}

		String token = UUID.randomUUID().toString();

		String password = SecurityUtility.randomPassword();
		user.setPassword(passwordEncoder.encode(password));

		user.setUserRole(UserRole.user);
		user.setUserStatus(UserStatus.active);
		user.setCreatedAt(LocalDateTime.now());
		user.setStartJoinDate(LocalDate.now());
		User createdUser = userService.save(user);

		userService.createPasswordResetTokenForUser(createdUser, token);

		String frontendUrl = "http://localhost:8081";

		SimpleMailMessage sendEmail = mailConstructor.constructResetTokenEmail(frontendUrl, request.getLocale(), token,
				user, password, "Champion's Bookstore - New User");

		mailSender.send(sendEmail);

		return ResponseEntity.ok().body("Email is Send.");

	}

	@PostMapping("/login")
	private ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginReq) {

		User user = userService.checkLoginUser(loginReq.getEmail(), loginReq.getPassword());

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(user);

	}

	@PostMapping("/newUser")
	private ResponseEntity<?> newUser(@RequestBody PasswordResetToken pw) {

		PasswordResetToken passwordResetToken = userService.getPasswordResetToken(pw.getToken());

		if (passwordResetToken == null) {
			return ResponseEntity.badRequest().body("Token is not exists.");
		}

		User loginUser = passwordResetToken.getUser();

		return ResponseEntity.ok().body(loginUser);

	}

	@PostMapping("/forget/password")
	private ResponseEntity<?> forgetPassword(@RequestParam("email") String email, HttpServletRequest request) {

		User user = userService.findByEmail(email);

		if (user == null) {
			return ResponseEntity.badRequest().body("Email is not Exist");
		}

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

		user.setPassword(encryptedPassword);

		userService.update(user);

		String frontendUrl = "http://localhost:8081";
		
		String token = user.getPasswordResetToken().getToken();
		
		SimpleMailMessage sendEmail = mailConstructor.constructResetTokenEmail(frontendUrl, request.getLocale(), token,
				user, password, "Champion's Bookstore - Forget Password");

		mailSender.send(sendEmail);
		
		return ResponseEntity.ok().body("Email is Send");
		
	}

	@GetMapping("/profile")
	private ResponseEntity<?> getProfile(@RequestParam("userId") Long userId) {

		User user = userService.findById(userId);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(user);

	}

	@PutMapping("/profile/update")
	private ResponseEntity<?> updateProfile(@Valid @RequestBody User user,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) {

		User localUser = userService.findById(user.getId());

		if (localUser == null) {
			return ResponseEntity.notFound().build();
		}

		if (userService.findByEmail(user.getEmail()) != null) {
			if ((userService.findByEmail(user.getEmail())).getId() != localUser.getId()) {
				return ResponseEntity.badRequest().body("Email Already Exists");
			}
		}

		String dbPassword = localUser.getPassword();

		if (passwordEncoder.matches(currentPassword, dbPassword)) {

			if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {

				localUser.setPassword(passwordEncoder.encode(newPassword));

			}

		} else {
			return ResponseEntity.notFound().build();
		}

		localUser.setFirstName(user.getFirstName());
		localUser.setLastName(user.getLastName());
		localUser.setUsername(user.getUsername());
		localUser.setPhone(user.getPhone());
		localUser.setEmail(user.getEmail());
		localUser.setStartJoinDate(user.getStartJoinDate());
		localUser.setUpdatedAt(LocalDateTime.now());
		userService.update(localUser);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/shipping/add")
	private ResponseEntity<?> addNewShippingAddress(@Valid @RequestBody UserAddress userAddress,
			@RequestParam("currentUserId") Long currentUserId) {

		UserAddress newUserAddress = new UserAddress();
		newUserAddress.setUserAddressId(userAddress.getUserAddressId());
		newUserAddress.setUserAddressName(userAddress.getUserAddressName());
		newUserAddress.setUserAddressStreet1(userAddress.getUserAddressStreet1());
		newUserAddress.setUserAddressStreet2(userAddress.getUserAddressStreet2());
		newUserAddress.setUserAddressCity(userAddress.getUserAddressCity());
		newUserAddress.setUserAddressState(userAddress.getUserAddressState());
		newUserAddress.setUserAddressCountry("US");
		newUserAddress.setUserAddressZipcode(userAddress.getUserAddressZipcode());

		User user = userService.findById(currentUserId);

		userService.addUserShipping(newUserAddress, user);

		return ResponseEntity.ok().body(newUserAddress);

	}

	@PutMapping("/shipping/update")
	private ResponseEntity<?> updateShippingAddress(@Valid @RequestBody UserAddress userAddress,
			@RequestParam("currentUserId") Long currentUserId) {

		UserAddress newUserAddress = new UserAddress();
		newUserAddress.setUserAddressId(userAddress.getUserAddressId());
		newUserAddress.setUserAddressName(userAddress.getUserAddressName());
		newUserAddress.setUserAddressStreet1(userAddress.getUserAddressStreet1());
		newUserAddress.setUserAddressStreet2(userAddress.getUserAddressStreet2());
		newUserAddress.setUserAddressCity(userAddress.getUserAddressCity());
		newUserAddress.setUserAddressState(userAddress.getUserAddressState());
		newUserAddress.setUserAddressCountry("US");
		newUserAddress.setUserAddressZipcode(userAddress.getUserAddressZipcode());

		User user = userService.findById(currentUserId);

		userService.updateUserShipping(newUserAddress, user);

		return ResponseEntity.ok().body(newUserAddress);

	}

	@PostMapping("/card/add")
	private ResponseEntity<?> addNewCreditCard(@Valid @RequestBody UserBillingPayment userBillPayment,
			@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		UserPaymentMethod newUserPayment = new UserPaymentMethod();
		newUserPayment.setId(userBillPayment.getUserPaymentMethod().getId());
		newUserPayment.setCardName(userBillPayment.getUserPaymentMethod().getCardName());
		newUserPayment.setCardNumber(userBillPayment.getUserPaymentMethod().getCardNumber());
		newUserPayment.setCardType(userBillPayment.getUserPaymentMethod().getCardType());
		newUserPayment.setCvc(userBillPayment.getUserPaymentMethod().getCvc());
		newUserPayment.setHolderName(userBillPayment.getUserPaymentMethod().getHolderName());
		newUserPayment.setExpiryMonth(userBillPayment.getUserPaymentMethod().getExpiryMonth());
		newUserPayment.setExpiryYear(userBillPayment.getUserPaymentMethod().getExpiryYear());
		newUserPayment.setDefaultPayment(userBillPayment.getUserPaymentMethod().getDefaultPayment());
		newUserPayment.setUserBilling(userBillPayment.getUserPaymentMethod().getUserBilling());

		UserAddress userBilling = new UserAddress();
		userBilling.setUserAddressId(userBillPayment.getUserAddress().getUserAddressId());
		userBilling.setUserAddressName(userBillPayment.getUserAddress().getUserAddressName());
		userBilling.setUserAddressStreet1(userBillPayment.getUserAddress().getUserAddressStreet1());
		userBilling.setUserAddressStreet2(userBillPayment.getUserAddress().getUserAddressStreet2());
		userBilling.setUserAddressCity(userBillPayment.getUserAddress().getUserAddressCity());
		userBilling.setUserAddressState(userBillPayment.getUserAddress().getUserAddressState());
		userBilling.setUserAddressCountry("US");
		userBilling.setUserAddressZipcode(userBillPayment.getUserAddress().getUserAddressZipcode());

		userService.addUserBilling(userBilling, newUserPayment, user);

		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();

		return ResponseEntity.ok().body(userPaymentList);

	}

	@PutMapping("/card/update")
	private ResponseEntity<?> updateCreditCard(@Valid @RequestBody UserBillingPayment userBillPayment,
			@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		UserPaymentMethod newUserPayment = new UserPaymentMethod();
		newUserPayment.setId(userBillPayment.getUserPaymentMethod().getId());
		newUserPayment.setCardName(userBillPayment.getUserPaymentMethod().getCardName());
		newUserPayment.setCardNumber(userBillPayment.getUserPaymentMethod().getCardNumber());
		newUserPayment.setCardType(userBillPayment.getUserPaymentMethod().getCardType());
		newUserPayment.setCvc(userBillPayment.getUserPaymentMethod().getCvc());
		newUserPayment.setHolderName(userBillPayment.getUserPaymentMethod().getHolderName());
		newUserPayment.setExpiryMonth(userBillPayment.getUserPaymentMethod().getExpiryMonth());
		newUserPayment.setExpiryYear(userBillPayment.getUserPaymentMethod().getExpiryYear());
//		newUserPayment.setUser(user);
		newUserPayment.setDefaultPayment(userBillPayment.getUserPaymentMethod().getDefaultPayment());

		UserAddress userBilling = new UserAddress();
		userBilling.setUserAddressId(userBillPayment.getUserAddress().getUserAddressId());
		userBilling.setUserAddressName(userBillPayment.getUserAddress().getUserAddressName());
		userBilling.setUserAddressStreet1(userBillPayment.getUserAddress().getUserAddressStreet1());
		userBilling.setUserAddressStreet2(userBillPayment.getUserAddress().getUserAddressStreet2());
		userBilling.setUserAddressCity(userBillPayment.getUserAddress().getUserAddressCity());
		userBilling.setUserAddressState(userBillPayment.getUserAddress().getUserAddressState());
		userBilling.setUserAddressCountry("US");
		userBilling.setUserAddressZipcode(userBillPayment.getUserAddress().getUserAddressZipcode());

		userService.updateUserBilling(userBilling, newUserPayment, user);

		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();

		return ResponseEntity.ok().body(userPaymentList);

	}

	@GetMapping("/shipping/list")
	private ResponseEntity<?> listOfShippingAddress(@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		List<UserShipping> userShippingList = user.getUserShippingList();

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);

		StateShippingList stateShippingList = new StateShippingList();
		stateShippingList.setStateList(stateList);
		stateShippingList.setUserShippingList(userShippingList);

		return ResponseEntity.ok(stateShippingList);

	}

	@GetMapping("/card/list")
	private ResponseEntity<?> listOfCreditCards(@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);
		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();

		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);

		StatePaymentList statePaymentList = new StatePaymentList();
		statePaymentList.setStateList(stateList);
		statePaymentList.setUserPaymentMethodList(userPaymentList);

		return ResponseEntity.ok(statePaymentList);

	}

	@DeleteMapping("/shipping/remove")
	private ResponseEntity<?> removeUserShipping(@RequestParam("userShippingId") Long userShippingId,
			@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		UserShipping userShipping = userShippingService.findById(userShippingId);

		if (user.getId() != userShipping.getUser().getId()) {
			return ResponseEntity.badRequest().build();
		}

		userShippingService.removeById(userShippingId);
		userAddressService.removeById(userShipping.getUserAddress().getUserAddressId());

		List<UserAddress> userAddressList = new ArrayList<>();

		List<UserShipping> userShippingList = user.getUserShippingList();
		for (UserShipping userShipping1 : userShippingList) {
			UserAddress userAddress = new UserAddress();
			userAddress.setUserAddressId(userShipping1.getUserAddress().getUserAddressId());
			userAddress.setUserAddressName(userShipping1.getUserAddress().getUserAddressName());
			userAddress.setUserAddressStreet1(userShipping1.getUserAddress().getUserAddressStreet1());
			userAddress.setUserAddressStreet2(userShipping1.getUserAddress().getUserAddressStreet2());
			userAddress.setUserAddressCity(userShipping1.getUserAddress().getUserAddressCity());
			userAddress.setUserAddressState(userShipping1.getUserAddress().getUserAddressState());
			userAddress.setUserAddressCountry(userShipping1.getUserAddress().getUserAddressCountry());
			userAddress.setUserAddressZipcode(userShipping1.getUserAddress().getUserAddressZipcode());
			userAddressList.add(userAddress);
		}

		return ResponseEntity.ok(userAddressList);

	}

	@DeleteMapping("/card/remove")
	private ResponseEntity<?> removeCreditCard(@RequestParam("id") Long userPaymentId,
			@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		UserPaymentMethod userPayment = userPaymentMethodService.findById(userPaymentId);

		if (user.getId() != userPayment.getUser().getId()) {
			return ResponseEntity.badRequest().build();
		}

		userPaymentMethodService.removeById(userPaymentId);
		userAddressService.removeById(userPayment.getUserBilling().getUserAddress().getUserAddressId());

		return ResponseEntity.ok().build();

	}

	@PostMapping("/shipping/default")
	private ResponseEntity<?> setDefaultShippingAddress(@RequestParam("userShippingId") Long userShippingId,
			@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		userService.setUserDefaultShipping(userShippingId, user);

		List<UserShipping> userShippingList = user.getUserShippingList();

		return ResponseEntity.ok(userShippingList);

	}

	@PostMapping("/card/default")
	private ResponseEntity<?> setDefaultPayment(@RequestParam("userPaymentId") Long userPaymentId,
			@RequestParam("currentUserId") Long currentUserId) {

		User user = userService.findById(currentUserId);

		userService.setUserDefaultPayment(userPaymentId, user);

		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();

		return ResponseEntity.ok(userPaymentList);

	}

	@GetMapping("/order/list")
	private ResponseEntity<?> orderList(@RequestParam("userId") Long userId) {

		User user = userService.findById(userId);

		List<Order> orderList = user.getOrderList();

		return ResponseEntity.ok().body(orderList);

	}

	@GetMapping("/order/detail")
	private ResponseEntity<?> orderDetail(@RequestParam("orderId") Long orderId, @RequestParam("userId") Long userId) {

		User user = userService.findById(userId);

		Order order = orderService.findById(orderId);

		if (order.getUser().getId() != user.getId()) {
			return ResponseEntity.badRequest().build();
		}

		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
		orderDetailsDto.setOrder(order);
		orderDetailsDto.setUserCartItemList(order.getCartItemList());
		orderDetailsDto.setUserOrderAddressList(order.getUserOrderAddressList());
		orderDetailsDto.setUserTransaction(order.getUserTransaction());

		return ResponseEntity.ok().body(orderDetailsDto);

	}

}
