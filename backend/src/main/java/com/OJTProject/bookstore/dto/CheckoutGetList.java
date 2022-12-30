package com.OJTProject.bookstore.dto;

import java.util.List;

import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.entity.UserOrderAddress;
import com.OJTProject.bookstore.entity.UserPaymentMethod;
import com.OJTProject.bookstore.entity.UserShipping;
import com.OJTProject.bookstore.entity.UserTransaction;

public class CheckoutGetList {

	private List<UserShipping> userShippingList;

	private List<UserPaymentMethod> userPaymentMethodList;

	private List<UserCartItem> userCartItemList;

	private List<UserOrderAddress> userOrderAddressList;

	private UserTransaction userTransaction;

	private UserCart userCart;

	private List<String> stateList;

	public CheckoutGetList() {
	}

	public List<UserShipping> getUserShippingList() {
		return userShippingList;
	}

	public void setUserShippingList(List<UserShipping> userShippingList) {
		this.userShippingList = userShippingList;
	}

	public List<UserPaymentMethod> getUserPaymentMethodList() {
		return userPaymentMethodList;
	}

	public void setUserPaymentMethodList(List<UserPaymentMethod> userPaymentMethodList) {
		this.userPaymentMethodList = userPaymentMethodList;
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

	public UserCart getUserCart() {
		return userCart;
	}

	public void setUserCart(UserCart userCart) {
		this.userCart = userCart;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

}
