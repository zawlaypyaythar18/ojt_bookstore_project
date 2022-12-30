package com.OJTProject.bookstore.dto;

import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;

public class UserBillingPayment {

	private UserAddress userAddress;

	private UserPaymentMethod userPaymentMethod;

	public UserBillingPayment() {
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public UserPaymentMethod getUserPaymentMethod() {
		return userPaymentMethod;
	}

	public void setUserPaymentMethod(UserPaymentMethod userPaymentMethod) {
		this.userPaymentMethod = userPaymentMethod;
	}

}
