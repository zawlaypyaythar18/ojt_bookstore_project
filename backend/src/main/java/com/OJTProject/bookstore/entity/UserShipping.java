package com.OJTProject.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserShipping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userShippingId;

	private Boolean userShippingDefault;

	@ManyToOne
	@JoinColumn(name = "userAddressId")
	private UserAddress userAddress;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public UserShipping() {
	}

	public Long getUserShippingId() {
		return userShippingId;
	}

	public void setUserShippingId(Long userShippingId) {
		this.userShippingId = userShippingId;
	}

	public Boolean getUserShippingDefault() {
		return userShippingDefault;
	}

	public void setUserShippingDefault(Boolean userShippingDefault) {
		this.userShippingDefault = userShippingDefault;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
