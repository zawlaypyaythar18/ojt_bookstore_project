package com.OJTProject.bookstore.service.impl;

import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.ShippingBill;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.service.ShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Override
	public UserOrderAddress setByUserShipping(UserShipping userShipping, UserOrderAddress userOrderAddress) {

		userOrderAddress.setAddressName(userShipping.getUserAddress().getUserAddressName());
		userOrderAddress.setAddressStreet1(userShipping.getUserAddress().getUserAddressStreet1());
		userOrderAddress.setAddressStreet2(userShipping.getUserAddress().getUserAddressStreet2());
		userOrderAddress.setAddressCity(userShipping.getUserAddress().getUserAddressCity());
		userOrderAddress.setAddressState(userShipping.getUserAddress().getUserAddressState());
		userOrderAddress.setAddressCountry(userShipping.getUserAddress().getUserAddressCountry());
		userOrderAddress.setAddressZipcode(userShipping.getUserAddress().getUserAddressZipcode());
		userOrderAddress.setShippingBill(ShippingBill.shipping);

		return userOrderAddress;
	}

}
