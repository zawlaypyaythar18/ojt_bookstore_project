package com.OJTProject.bookstore.service.impl;

import org.springframework.stereotype.Service;

import com.OJTProject.bookstore.entity.ShippingBill;
import com.OJTProject.bookstore.entity.UserBilling;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.service.BillingAddressService;

@Service
public class BillingAddressServiceImpl implements BillingAddressService {

	@Override
	public UserOrderAddress setByUserBilling(UserBilling userBilling, UserOrderAddress userOrderAddress) {

		userOrderAddress.setAddressName(userBilling.getUserAddress().getUserAddressName());
		userOrderAddress.setAddressStreet1(userBilling.getUserAddress().getUserAddressStreet1());
		userOrderAddress.setAddressStreet2(userBilling.getUserAddress().getUserAddressStreet2());
		userOrderAddress.setAddressCity(userBilling.getUserAddress().getUserAddressCity());
		userOrderAddress.setAddressState(userBilling.getUserAddress().getUserAddressState());
		userOrderAddress.setAddressCountry(userBilling.getUserAddress().getUserAddressCountry());
		userOrderAddress.setAddressZipcode(userBilling.getUserAddress().getUserAddressZipcode());
		userOrderAddress.setShippingBill(ShippingBill.billing);

		return userOrderAddress;
	}

}
