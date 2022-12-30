package com.OJTProject.bookstore.service;

import java.util.List;

import com.OJTProject.bookstore.entity.Book;
import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;

public interface UserCartItemService {

	List<UserCartItem> findByUserCart(UserCart userCart);

	UserCartItem updateCartItem(UserCartItem userCartItem);

	UserCartItem addBookToCartItem(Book book, User user, Integer qty);

	UserCartItem findById(Long cartItemId);

	UserCartItem save(UserCartItem userCartItem);


	void removeCartItem(UserCartItem userCartItem);

}
