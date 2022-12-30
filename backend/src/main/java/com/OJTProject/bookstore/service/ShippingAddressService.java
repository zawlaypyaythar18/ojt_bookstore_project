package com.OJTProject.bookstore.service;

import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserShipping;

public interface ShippingAddressService {

	UserOrderAddress setByUserShipping(UserShipping userShipping, UserOrderAddress userOrderAddress);

}
