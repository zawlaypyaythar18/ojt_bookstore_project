package com.OJTProject.bookstore.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String username;
	
	@Column(length = 100, nullable = false, unique = true)
	@NotBlank(message = "Required")
	private String email;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Required")
	private String password;
	
	@Column(columnDefinition = "ENUM('active','deactivated') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	
	@Column(columnDefinition = "ENUM('user','admin') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@Column(length = 100)
	private String firstName;
	
	@Column(length = 100)
	private String lastName;
	
	@Column(length = 50)
	private String phone;

	private LocalDate startJoinDate;

	private LocalDate lastJoinDate;

	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	private PasswordResetToken passwordResetToken;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	@JsonIgnore
	private UserCart userCart;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	@JsonIgnore
	private List<UserShipping> userShippingList;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	@JsonIgnore
	private List<UserPaymentMethod> userPaymentMethodList;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Order> orderList;
	
	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getStartJoinDate() {
		return startJoinDate;
	}

	public void setStartJoinDate(LocalDate startJoinDate) {
		this.startJoinDate = startJoinDate;
	}

	public LocalDate getLastJoinDate() {
		return lastJoinDate;
	}

	public void setLastJoinDate(LocalDate lastJoinDate) {
		this.lastJoinDate = lastJoinDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserCart getUserCart() {
		return userCart;
	}

	public void setUserCart(UserCart userCart) {
		this.userCart = userCart;
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

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	

}
