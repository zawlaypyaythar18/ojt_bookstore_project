package com.OJTProject.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.UserBilling;
import com.OJTProject.bookstore.repo.UserBillingRepo;
import com.OJTProject.bookstore.service.UserBillingService;

@Service
public class UserBillingServiceImpl implements UserBillingService {
	
	@Autowired
	private UserBillingRepo userBillingRepo;

	@Override
	public UserBilling findById(Long id) {
		return userBillingRepo.findById(id).orElse(null);
	}

	@Override
	public void removeById(Long id) {
		userBillingRepo.deleteById(id);
	}

}
