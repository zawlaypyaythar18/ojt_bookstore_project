package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.UserBilling;

public interface UserBillingRepo extends JpaRepository<UserBilling, Long> {
	
}
