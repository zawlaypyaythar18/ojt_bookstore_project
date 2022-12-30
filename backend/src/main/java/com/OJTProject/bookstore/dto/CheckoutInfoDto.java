package com.OJTProject.bookstore.dto;

import java.util.List;

import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserTransaction;

public class CheckoutInfoDto {

	private List<UserOrderAddress> userOrderAddressList;

	private UserTransaction userTransaction;

	public CheckoutInfoDto() {
	}

	public List<UserOrderAddress> getUserOrderAddressList() {
		return userOrderAddressList;
	}

	public void setUserOrderAddressList(List<UserOrderAddress> userOrderAddressList) {
		this.userOrderAddressList = userOrderAddressList;
	}

	public UserTransaction getUserTransaction() {
		return userTransaction;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

}
