package com.OJTProject.bookstore.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.entity.LoginRequest;
import com.OJTProject.bookstore.entity.PasswordResetToken;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserRole;
import com.OJTProject.bookstore.entity.UserStatus;
import com.OJTProject.bookstore.service.UserService;
import com.OJTProject.bookstore.utility.MailConstructor;
import com.OJTProject.bookstore.utility.SecurityUtility;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping("/register")
	private ResponseEntity<?> register(@Valid @RequestBody User user,HttpServletRequest request) throws IOException {
		
		if (userService.findByEmail(user.getEmail()) != null) {
			return ResponseEntity.badRequest().body("Email is already Exists");
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
		
		SimpleMailMessage sendEmail = mailConstructor.constructResetTokenEmail(frontendUrl, request.getLocale(), token, user, password, "Champion's Bookstore - New User");
		
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
	
}
