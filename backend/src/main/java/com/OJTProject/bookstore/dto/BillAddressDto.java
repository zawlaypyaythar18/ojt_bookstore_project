package com.OJTProject.bookstore.dto;

import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserTransaction;

public class BillAddressDto {

	private UserTransaction userTransaction;

	private UserOrderAddress userOrderAddress;

	public BillAddressDto() {
	}

	public UserTransaction getUserTransaction() {
		return userTransaction;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	public UserOrderAddress getUserOrderAddress() {
		return userOrderAddress;
	}

	public void setUserOrderAddress(UserOrderAddress userOrderAddress) {
		this.userOrderAddress = userOrderAddress;
	}

}
