package com.OJTProject.bookstore.dto;

import java.util.List;

import com.OJTProject.bookstore.entity.UserAddress;
import com.OJTProject.bookstore.entity.UserShipping;

public class StateShippingList {

	private List<UserAddress> userAddressList;

	private List<String> stateList;

	private List<UserShipping> userShippingList;

	public List<UserAddress> getUserAddressList() {
		return userAddressList;
	}

	public void setUserAddressList(List<UserAddress> userAddressList) {
		this.userAddressList = userAddressList;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	public List<UserShipping> getUserShippingList() {
		return userShippingList;
	}

	public void setUserShippingList(List<UserShipping> userShippingList) {
		this.userShippingList = userShippingList;
	}

}
