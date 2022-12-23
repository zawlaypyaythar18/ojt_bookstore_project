package com.OJTProject.bookstore.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserCartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer qty;
	
	private BigDecimal subTotal;
	
//	@OneToOne
//	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "userCartId")
	private UserCart userCart;
	
	@OneToMany(mappedBy = "cartItem")
	@JsonIgnore
	private List<UserCartItemBook> userCartItemBook;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;
	
	public UserCartItem() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	

//	public Book getBook() {
//		return book;
//	}
//
//	public void setBook(Book book) {
//		this.book = book;
//	}

	public UserCart getUserCart() {
		return userCart;
	}

	public void setUserCart(UserCart userCart) {
		this.userCart = userCart;
	}

	public List<UserCartItemBook> getUserCartItemBook() {
		return userCartItemBook;
	}

	public void setUserCartItemBook(List<UserCartItemBook> userCartItemBook) {
		this.userCartItemBook = userCartItemBook;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	

}
