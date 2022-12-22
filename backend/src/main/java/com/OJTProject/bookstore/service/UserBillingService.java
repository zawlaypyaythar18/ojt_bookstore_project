package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserBilling;

public interface UserBillingService {
	
	UserBilling findById(Long id);
	
	void removeById(Long id);

}
