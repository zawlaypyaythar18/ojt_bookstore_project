package com.OJTProject.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.UserAddress;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {

}
