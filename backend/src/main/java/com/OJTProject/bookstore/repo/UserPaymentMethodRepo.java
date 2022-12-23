package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.UserPaymentMethod;

public interface UserPaymentMethodRepo extends JpaRepository<UserPaymentMethod, Long> {

}
