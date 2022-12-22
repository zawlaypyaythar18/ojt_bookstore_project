package com.OJTProject.bookstore.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserBilling {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userBillingId;
	
	@ManyToOne
	@JoinColumn(name = "userAddressId")
	private UserAddress userAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private UserPaymentMethod userPaymentMethod;
	
	public UserBilling() {}

	public Long getUserBillingId() {
		return userBillingId;
	}

	public void setUserBillingId(Long userBillingId) {
		this.userBillingId = userBillingId;
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
