package com.OJTProject.bookstore.repo;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OJTProject.bookstore.entity.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

}
