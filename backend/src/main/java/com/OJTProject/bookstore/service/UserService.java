package com.OJTProject.bookstore.service;

import java.util.List;

import com.OJTProject.bookstore.entity.PasswordResetToken;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;

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
	
	//boolean deletePasswordResetTokenByUser(Long user_id);
	
	void updateUserBilling(UserAddress userAddress,UserPaymentMethod userPayment,User user);
	
	void updateUserShipping(UserAddress userAddress,User user);
	
	void addUserBilling(UserAddress userAddress,UserPaymentMethod userPayment,User user);
	
	void addUserShipping(UserAddress userAddress,User user);
	
	void setUserDefaultPayment(Long userPaymentId,User user);
	
	void setUserDefaultShipping(Long userShippingId,User user);

}
