package com.OJTProject.bookstore.service;

import java.util.List;

import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserTransaction;

public interface OrderService {

	Order createOrder(UserCart userCart, List<UserOrderAddress> userOrderAddressList, UserTransaction userTransaction,
			String shippingMethod, User user);

	Order findById(Long orderId);

	List<Order> findAll();
	
	void deleteOrderById(Long orderId);
}
