package com.OJTProject.bookstore.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userAddressId;

	@NotBlank(message = "Required")
	private String userAddressName;

	@NotBlank(message = "Required")
	private String userAddressStreet1;

	private String userAddressStreet2;

	@NotBlank(message = "Required")
	private String userAddressCity;

	@NotBlank(message = "Required")
	private String userAddressState;

	private String userAddressCountry;

	@NotBlank(message = "Required")
	private String userAddressZipcode;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userAddress")
	@JsonIgnore
	private List<UserShipping> userShippingList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userAddress")
	@JsonIgnore
	private List<UserBilling> userBillingList;

	public Long getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(Long userAddressId) {
		this.userAddressId = userAddressId;
	}

	public String getUserAddressName() {
		return userAddressName;
	}

	public void setUserAddressName(String userAddressName) {
		this.userAddressName = userAddressName;
	}

	public String getUserAddressStreet1() {
		return userAddressStreet1;
	}

	public void setUserAddressStreet1(String userAddressStreet1) {
		this.userAddressStreet1 = userAddressStreet1;
	}

	public String getUserAddressStreet2() {
		return userAddressStreet2;
	}

	public void setUserAddressStreet2(String userAddressStreet2) {
		this.userAddressStreet2 = userAddressStreet2;
	}

	public String getUserAddressCity() {
		return userAddressCity;
	}

	public void setUserAddressCity(String userAddressCity) {
		this.userAddressCity = userAddressCity;
	}

	public String getUserAddressState() {
		return userAddressState;
	}

	public void setUserAddressState(String userAddressState) {
		this.userAddressState = userAddressState;
	}

	public String getUserAddressCountry() {
		return userAddressCountry;
	}

	public void setUserAddressCountry(String userAddressCountry) {
		this.userAddressCountry = userAddressCountry;
	}

	public String getUserAddressZipcode() {
		return userAddressZipcode;
	}

	public void setUserAddressZipcode(String userAddressZipcode) {
		this.userAddressZipcode = userAddressZipcode;
	}

	public List<UserShipping> getUserShippingList() {
		return userShippingList;
	}

	public void setUserShippingList(List<UserShipping> userShippingList) {
		this.userShippingList = userShippingList;
	}

	public List<UserBilling> getUserBillingList() {
		return userBillingList;
	}

	public void setUserBillingList(List<UserBilling> userBillingList) {
		this.userBillingList = userBillingList;
	}

}
