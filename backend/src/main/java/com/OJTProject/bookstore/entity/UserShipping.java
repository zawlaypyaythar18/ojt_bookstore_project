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

	private String userShippingName;

	private String userShippingStreet1;

	private String userShippingStreet2;

	private String userShippingCity;

	private String userShippingState;
	
	private String userShippingCountry;
	
	private String userShippingZipcode;
	
	private Boolean userShippingDefault;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	public UserShipping() {}

	public Long getUserShippingId() {
		return userShippingId;
	}

	public void setUserShippingId(Long userShippingId) {
		this.userShippingId = userShippingId;
	}

	public String getUserShippingName() {
		return userShippingName;
	}

	public void setUserShippingName(String userShippingName) {
		this.userShippingName = userShippingName;
	}

	public String getUserShippingStreet1() {
		return userShippingStreet1;
	}

	public void setUserShippingStreet1(String userShippingStreet1) {
		this.userShippingStreet1 = userShippingStreet1;
	}

	public String getUserShippingStreet2() {
		return userShippingStreet2;
	}

	public void setUserShippingStreet2(String userShippingStreet2) {
		this.userShippingStreet2 = userShippingStreet2;
	}

	public String getUserShippingCity() {
		return userShippingCity;
	}

	public void setUserShippingCity(String userShippingCity) {
		this.userShippingCity = userShippingCity;
	}

	public String getUserShippingState() {
		return userShippingState;
	}

	public void setUserShippingState(String userShippingState) {
		this.userShippingState = userShippingState;
	}

	public String getUserShippingCountry() {
		return userShippingCountry;
	}

	public void setUserShippingCountry(String userShippingCountry) {
		this.userShippingCountry = userShippingCountry;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserShippingZipcode() {
		return userShippingZipcode;
	}

	public void setUserShippingZipcode(String userShippingZipcode) {
		this.userShippingZipcode = userShippingZipcode;
	}

	public Boolean getUserShippingDefault() {
		return userShippingDefault;
	}

	public void setUserShippingDefault(Boolean userShippingDefault) {
		this.userShippingDefault = userShippingDefault;
	}

	@Override
	public String toString() {
		return "UserShipping [userShippingId=" + userShippingId + ", userShippingName=" + userShippingName
				+ ", userShippingStreet1=" + userShippingStreet1 + ", userShippingStreet2=" + userShippingStreet2
				+ ", userShippingCity=" + userShippingCity + ", userShippingState=" + userShippingState
				+ ", userShippingCountry=" + userShippingCountry + ", userShippingZipcode=" + userShippingZipcode
				+ ", userShippingDefault=" + userShippingDefault + ", user=" + user + "]";
	}
	
}
