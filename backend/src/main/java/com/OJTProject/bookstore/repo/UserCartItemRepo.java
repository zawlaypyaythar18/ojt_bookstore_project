package com.OJTProject.bookstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.UserCart;
import com.OJTProject.bookstore.entity.UserCartItem;

public interface UserCartItemRepo extends JpaRepository<UserCartItem, Long> {

	List<UserCartItem> findByUserCart(UserCart userCart);
	
}
