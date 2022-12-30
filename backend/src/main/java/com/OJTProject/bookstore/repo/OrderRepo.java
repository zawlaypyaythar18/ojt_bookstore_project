package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
