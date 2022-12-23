package com.OJTProject.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserBilling;
import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.repo.UserAddressRepo;
import com.OJTProject.bookstore.repo.UserBillingRepo;
import com.OJTProject.bookstore.repo.UserPaymentMethodRepo;
import com.OJTProject.bookstore.repo.UserShippingRepo;
import com.OJTProject.bookstore.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {
	
	@Autowired
	private UserShippingRepo userShippingRepo;
	
	@Autowired
	private UserAddressRepo userAddressRepo;
	
	@Autowired
	private UserPaymentMethodRepo userPaymentMethodRepo;
	
	@Autowired
	private UserBillingRepo userBillingRepo;

	@Override
	public UserAddress addUserShipping(UserAddress userAddress, User user) {
		
		userAddressRepo.save(userAddress);
		
		UserShipping userShipping = new UserShipping();
		userShipping.setUser(user);
		userShipping.setUserAddress(userAddress);
		userShipping.setUserShippingDefault(true);
		userShippingRepo.save(userShipping);
		
		return userAddress;
	}

	@Override
	public UserAddress addUserPaymentBilling(UserAddress userAddress, UserPaymentMethod userPaymentMethod, User user) {
		
		userAddressRepo.save(userAddress);
		user.getUserPaymentMethodList().add(userPaymentMethod);
		
		UserBilling userBilling = new UserBilling();
		userBilling.setUserAddress(userAddress);
		userBilling.setUserPaymentMethod(userPaymentMethod);
		userPaymentMethod.setUserBilling(userBilling);
		userBillingRepo.save(userBilling);
		
		userPaymentMethodRepo.save(userPaymentMethod);
		
		return userAddress;
	}

	@Override
	public UserAddress findById(Long userAddressId) {
		return userAddressRepo.findById(userAddressId).orElse(null);
	}

	@Override
	public void removeById(Long userAddressId) {
		userAddressRepo.deleteById(userAddressId);
	}

}
