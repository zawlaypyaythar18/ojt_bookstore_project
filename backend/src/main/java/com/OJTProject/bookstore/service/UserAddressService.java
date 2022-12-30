package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;

public interface UserAddressService {

	UserAddress addUserShipping(UserAddress userAddress, User user);

	UserAddress addUserPaymentBilling(UserAddress userAddress, UserPaymentMethod userPaymentMethod, User user);

	UserAddress findById(Long userAddressId);

	void removeById(Long userAddressId);

}
