package com.OJTProject.bookstore.dto;

import java.util.List;

import com.OJTProject.bookstore.entity.Order;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserTransaction;

public class OrderDetailsDto {

	private List<UserCartItem> userCartItemList;

	private List<UserOrderAddress> userOrderAddressList;

	private UserTransaction userTransaction;

	private Order order;

	public OrderDetailsDto() {
	}

	public List<UserCartItem> getUserCartItemList() {
		return userCartItemList;
	}

	public void setUserCartItemList(List<UserCartItem> userCartItemList) {
		this.userCartItemList = userCartItemList;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
