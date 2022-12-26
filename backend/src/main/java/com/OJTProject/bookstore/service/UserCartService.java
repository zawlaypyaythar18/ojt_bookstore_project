package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserCart;

public interface UserCartService {
	
	UserCart updateUserCart(UserCart userCart);
	
	void clearUserCart(UserCart userCart);
	
	UserCart findById(Long userCartId);

}
