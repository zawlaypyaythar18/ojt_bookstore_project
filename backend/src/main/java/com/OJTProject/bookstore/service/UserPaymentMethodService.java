package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserPaymentMethod;

public interface UserPaymentMethodService {

	UserPaymentMethod findById(Long id);

	void removeById(Long id);

}
