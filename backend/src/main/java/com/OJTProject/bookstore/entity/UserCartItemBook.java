package com.OJTProject.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserCartItemBook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "bookId")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "cartItemId")
	private UserCartItem cartItem;

	public UserCartItemBook() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public UserCartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(UserCartItem cartItem) {
		this.cartItem = cartItem;
	}

	@Override
	public String toString() {
		return "BookToCartItem [id=" + id + ", book=" + book + ", cartItem=" + cartItem + "]";
	}

}
