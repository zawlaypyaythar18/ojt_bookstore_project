package com.OJTProject.bookstore.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class UserPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 100)
	private String cardType;
	
	@Column(length = 255)
	private String cardName;
	
	@Column(length = 255)
	private String cardNumber;
	
	private Integer expiryMonth;
	
	private Integer expiryYear;
	
	private Integer cvc;
	
	@Column(length = 255)
	private String holderName;
	
	private Boolean defaultPayment;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "userPayment")
	private UserBilling userBilling;
	
	public UserPayment() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(Integer expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public Integer getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(Integer expiryYear) {
		this.expiryYear = expiryYear;
	}

	public Integer getCvc() {
		return cvc;
	}

	public void setCvc(Integer cvc) {
		this.cvc = cvc;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Boolean getDefaultPayment() {
		return defaultPayment;
	}

	public void setDefaultPayment(Boolean defaultPayment) {
		this.defaultPayment = defaultPayment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserBilling getUserBilling() {
		return userBilling;
	}

	public void setUserBilling(UserBilling userBilling) {
		this.userBilling = userBilling;
	}

	@Override
	public String toString() {
		return "UserPayment [id=" + id + ", cardType=" + cardType + ", cardName=" + cardName + ", cardNumber="
				+ cardNumber + ", expiryMonth=" + expiryMonth + ", expiryYear=" + expiryYear + ", cvc=" + cvc
				+ ", holderName=" + holderName + ", defaultPayment=" + defaultPayment + ", user=" + user
				+ ", userBilling=" + userBilling + "]";
	}
	
}
