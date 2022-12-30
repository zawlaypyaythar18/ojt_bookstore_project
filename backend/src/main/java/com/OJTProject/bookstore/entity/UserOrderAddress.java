package com.OJTProject.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class UserOrderAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressId;

	@NotBlank(message = "Required")
	private String addressName;

	@NotBlank(message = "Required")
	private String addressStreet1;

	private String addressStreet2;

	@NotBlank(message = "Required")
	private String addressCity;

	@NotBlank(message = "Required")
	private String addressState;

	private String addressCountry;

	@NotBlank(message = "Required")
	private String addressZipcode;

	@Column(columnDefinition = "ENUM('shipping', 'billing') NOT NULL")
	@Enumerated(EnumType.STRING)
	private ShippingBill shippingBill;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public UserOrderAddress() {
		// TODO Auto-generated constructor stub
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressStreet1() {
		return addressStreet1;
	}

	public void setAddressStreet1(String addressStreet1) {
		this.addressStreet1 = addressStreet1;
	}

	public String getAddressStreet2() {
		return addressStreet2;
	}

	public void setAddressStreet2(String addressStreet2) {
		this.addressStreet2 = addressStreet2;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}

	public String getAddressZipcode() {
		return addressZipcode;
	}

	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}

	public ShippingBill getShippingBill() {
		return shippingBill;
	}

	public void setShippingBill(ShippingBill shippingBill) {
		this.shippingBill = shippingBill;
	}

}
