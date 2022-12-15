package com.OJTProject.bookstore.service;

import java.util.List;

import com.OJTProject.bookstore.entity.PasswordResetToken;
import com.OJTProject.bookstore.entity.User;

public interface UserService {
	
	List<User> findAll();
	
	User findById(Long id);
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	User save(User user);
	
	User update(User user);
	
	boolean deleteUser(Long id);
	
	User checkLoginUser(String email,String password);
	
	List<String> getAllStatus();
	
	User updateStatus(Long userId, String status);
	
	PasswordResetToken getPasswordResetToken(String token);
	
	void createPasswordResetTokenForUser(User user, String token);

}
