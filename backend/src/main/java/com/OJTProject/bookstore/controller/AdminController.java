package com.OJTProject.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/userList")
	private ResponseEntity<?> getUserList() {
		List<User> userList = userService.findAll();
		return ResponseEntity.ok().body(userList);
	}
	
	@GetMapping("/userStatus")
	private ResponseEntity<?> getAllStatus() {
		List<String> statusList = userService.getAllStatus();
		return ResponseEntity.ok().body(statusList);
	}
	
	@PutMapping("/user/updateStatus")
	public ResponseEntity<?> updateUserStatus(@RequestParam("userId") Long userId, @RequestParam("status") String status) {
		User user = userService.updateStatus(userId, status);
		if (user == null) {
			return ResponseEntity.badRequest()
					.body("User is invalid, Status is invalid");
		}
		return ResponseEntity.ok().build();
	}
	
}
