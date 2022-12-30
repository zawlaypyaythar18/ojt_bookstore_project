package com.OJTProject.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.repo.UserShippingRepo;
import com.OJTProject.bookstore.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

	@Autowired
	private UserShippingRepo userShippingRepo;

	@Override
	public UserShipping findById(Long id) {
		return userShippingRepo.findById(id).orElse(null);
	}

	@Override
	public void removeById(Long id) {
		userShippingRepo.deleteById(id);
	}

}
