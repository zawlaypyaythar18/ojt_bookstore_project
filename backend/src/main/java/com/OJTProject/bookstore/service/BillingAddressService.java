package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserBilling;
import com.OJTProject.bookstore.entity.UserOrderAddress;

public interface BillingAddressService {

	UserOrderAddress setByUserBilling(UserBilling userBilling, UserOrderAddress userOrderAddress);

}
