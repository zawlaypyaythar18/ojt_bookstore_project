package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	User findByUsername(String username);
	
}
