package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.UserCart;

public interface UserCartRepo extends JpaRepository<UserCart, Long> {

}
