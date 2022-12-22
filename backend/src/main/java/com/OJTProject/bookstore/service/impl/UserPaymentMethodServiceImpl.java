package com.OJTProject.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.repo.UserPaymentMethodRepo;
import com.OJTProject.bookstore.service.UserPaymentMethodService;

@Service
public class UserPaymentMethodServiceImpl implements UserPaymentMethodService {
	
	@Autowired
	private UserPaymentMethodRepo userPaymentMethodRepo;

	@Override
	public UserPaymentMethod findById(Long id) {
		return userPaymentMethodRepo.findById(id).orElse(null);
	}

	@Override
	public void removeById(Long id) {
		userPaymentMethodRepo.deleteById(id);
	}

}
