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
import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserBilling;
import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.entity.UserStatus;
import com.OJTProject.bookstore.repo.PasswordResetTokenRepo;
import com.OJTProject.bookstore.repo.UserAddressRepo;
import com.OJTProject.bookstore.repo.UserBillingRepo;
import com.OJTProject.bookstore.repo.UserPaymentMethodRepo;
import com.OJTProject.bookstore.repo.UserRepo;
import com.OJTProject.bookstore.repo.UserShippingRepo;
import com.OJTProject.bookstore.service.UserService;
import com.OJTProject.bookstore.utility.SecurityUtility;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordResetTokenRepo passwordResetTokenRepo;
	
	@Autowired
	private UserPaymentMethodRepo userPaymentRepo;
	
	@Autowired
	private UserBillingRepo userBillingRepo;
	
	@Autowired
	private UserAddressRepo userAddressRepo;
	
	@Autowired
	private UserShippingRepo userShippingRepo;
	
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
		//passwordResetTokenRepo.deletePasswordResetTokenByUser(id);
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

	@Override
	public void updateUserBilling(UserAddress userAddress, UserPaymentMethod userPayment, User user) {
		
		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();
		
		if (userPayment.getId() == null) {
			for (UserPaymentMethod userPayment1 : userPaymentList) {
				userPayment1.setDefaultPayment(false);
				userPaymentRepo.save(userPayment1);
			}
		}
		
		userPayment.setUser(user);
		userAddressRepo.save(userAddress);
		userPayment.setDefaultPayment(true);
		user.getUserPaymentMethodList().add(userPayment);
		userRepo.save(user);
		
		if (userPayment.getId() != null) {
			for (UserPaymentMethod userPayment1 : userPaymentList) {
				if (userPayment1.getId() != userPayment.getId()) {
					userPayment1.setDefaultPayment(false);
					userPaymentRepo.save(userPayment1);
				}
			}
		}
		
	}

	@Override
	public void updateUserShipping(UserAddress userAddress, User user) {
		
		List<UserShipping> userShippingList = user.getUserShippingList();
		
		for (UserShipping userShipping1 : userShippingList) {
			userShipping1.setUserShippingDefault(false);
			userShippingRepo.save(userShipping1);
		}
		
		
		userAddressRepo.save(userAddress);
		for (UserShipping userShipping : userShippingList) {
			if (userAddress.getUserAddressId() == userShipping.getUserAddress().getUserAddressId()) {
				userShipping.setUserShippingDefault(true);
				userShippingRepo.save(userShipping);
			}
		}
		userRepo.save(user);
		
	}

	@Override
	public void addUserBilling(UserAddress userAddress, UserPaymentMethod userPayment, User user) {
		
		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();
		
		if (userPayment.getId() == null) {
			for (UserPaymentMethod userPayment1 : userPaymentList) {
				userPayment1.setDefaultPayment(false);
				userPaymentRepo.save(userPayment1);
			}
		}
		
		userPayment.setUser(user);
		userAddressRepo.save(userAddress);
		userPayment.setDefaultPayment(true);
		UserBilling userBilling = new UserBilling();
		userPayment.setUserBilling(userBilling);
		userBilling.setUserAddress(userAddress);
		userBilling.setUserPaymentMethod(userPayment);
		userBillingRepo.save(userBilling);
		user.getUserPaymentMethodList().add(userPayment);
		userRepo.save(user);
		
		if (userPayment.getId() != null) {
			for (UserPaymentMethod userPayment1 : userPaymentList) {
				if (userPayment1.getId() != userPayment.getId()) {
					userPayment1.setDefaultPayment(false);
					userPaymentRepo.save(userPayment1);
				}
			}
		}
		
	}

	@Override
	public void addUserShipping(UserAddress userAddress, User user) {
		
		List<UserShipping> userShippingList = user.getUserShippingList();
		
		if (userAddress.getUserAddressId() == null) {
			for (UserShipping userShipping1 : userShippingList) {
				userShipping1.setUserShippingDefault(false);
				userShippingRepo.save(userShipping1);
			}
		}
		
		userAddressRepo.save(userAddress);
		UserShipping userShipping = new UserShipping();
		userShipping.setUserAddress(userAddress);
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		userShippingRepo.save(userShipping);
		user.getUserShippingList().add(userShipping);
		userRepo.save(user);
		
		if (userAddress.getUserAddressId() != null) {
			for(UserShipping userShipping1 : userShippingList) {
				if (userShipping1.getUserShippingId() != userShipping.getUserShippingId()) {
					userShipping1.setUserShippingDefault(false);
					userShippingRepo.save(userShipping1);
				}
			}
		}
		
	}

	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		
		List<UserPaymentMethod> userPaymentList = user.getUserPaymentMethodList();
		
		for (UserPaymentMethod userPayment : userPaymentList) {
			if (userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepo.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepo.save(userPayment);
			}
		}
		
	}

	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		
		List<UserShipping> userShippingList = user.getUserShippingList();
		
		for (UserShipping userShipping : userShippingList) {
			if (userShipping.getUserShippingId() == userShippingId) {
				userShipping.setUserShippingDefault(true);
				userShippingRepo.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepo.save(userShipping);
			}
		}
		
	}

	/*@Override
	public boolean deletePasswordResetTokenByUser(Long user_id) {
		passwordResetTokenRepo.deleteByUser(user_id);
		return true;
	}*/
}
