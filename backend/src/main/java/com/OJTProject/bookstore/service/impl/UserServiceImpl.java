package com.OJTProject.bookstore.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.PasswordResetToken;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserStatus;
import com.OJTProject.bookstore.repo.PasswordResetTokenRepo;
import com.OJTProject.bookstore.repo.UserRepo;
import com.OJTProject.bookstore.service.UserService;
import com.OJTProject.bookstore.utility.SecurityUtility;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordResetTokenRepo passwordResetTokenRepo;

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public User save(User user) {
		if (findByEmail(user.getEmail()) != null) {
			return null;
		}
		return userRepo.save(user);
	}

	@Override
	public User update(User user) {
		user.setUpdatedAt(LocalDateTime.now());
		return userRepo.save(user);
	}

	@Override
	public boolean deleteUser(Long id) {
		userRepo.deleteById(id);
		return true;
	}

	@Override
	public User checkLoginUser(String email, String password) {
		
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return null;
		}
		
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		
		if (passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		
		return null;
	}

	@Override
	public List<String> getAllStatus() {
		List<String> userStatusList = new ArrayList<>();
		for (UserStatus role : Arrays.asList(UserStatus.values())) {
			userStatusList.add(role.toString());
		}
		return userStatusList;
	}

	@Override
	public User updateStatus(Long userId, String status) {
		User updateUserStatus = findById(userId);
		if (updateUserStatus != null) {
			updateUserStatus.setUserStatus(UserStatus.valueOf(status));
			updateUserStatus.setUpdatedAt(LocalDateTime.now());
			userRepo.save(updateUserStatus);
			return updateUserStatus;
		}
		return updateUserStatus;
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		return passwordResetTokenRepo.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		
		final PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
		
		passwordResetTokenRepo.save(passwordResetToken);
		
	}

}
