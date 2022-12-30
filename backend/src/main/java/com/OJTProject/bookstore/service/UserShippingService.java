package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserShipping;

public interface UserShippingService {

	UserShipping findById(Long id);

	void removeById(Long id);

}
