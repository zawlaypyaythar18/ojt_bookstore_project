package com.OJTProject.bookstore.repo;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.OJTProject.bookstore.entity.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	@Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
	void deleteAllExpiryDateSince(Date now);
	
	//@Query("delete from PasswordResetToken p where p.user_id= :userID%")
	//void deleteByUser(@Param("userID") Long userID);
	void deletePasswordResetTokenByUser(Long id);
	
}
