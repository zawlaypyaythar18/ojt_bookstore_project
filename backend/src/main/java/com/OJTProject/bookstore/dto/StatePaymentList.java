package com.OJTProject.bookstore.dto;

import java.util.List;

import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;

public class StatePaymentList {

	private List<String> stateList;

	private List<UserPaymentMethod> userPaymentMethodList;

	private List<UserAddress> userAddressList;

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	public List<UserPaymentMethod> getUserPaymentMethodList() {
		return userPaymentMethodList;
	}

	public void setUserPaymentMethodList(List<UserPaymentMethod> userPaymentMethodList) {
		this.userPaymentMethodList = userPaymentMethodList;
	}

	public List<UserAddress> getUserAddressList() {
		return userAddressList;
	}

	public void setUserAddressList(List<UserAddress> userAddressList) {
		this.userAddressList = userAddressList;
	}

}
