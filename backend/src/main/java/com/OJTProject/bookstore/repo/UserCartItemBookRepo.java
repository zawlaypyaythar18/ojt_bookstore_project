package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.UserCartItem;
import com.OJTProject.bookstore.entity.UserCartItemBook;

public interface UserCartItemBookRepo extends JpaRepository<UserCartItemBook, Long> {

	void deleteByCartItem(UserCartItem userCartItem);

}
