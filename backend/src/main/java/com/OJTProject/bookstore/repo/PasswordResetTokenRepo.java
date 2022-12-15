package com.OJTProject.bookstore.repo;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OJTProject.bookstore.entity.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
	
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	@Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
	void deleteAllExpiryDateSince(Date now);
	
}
