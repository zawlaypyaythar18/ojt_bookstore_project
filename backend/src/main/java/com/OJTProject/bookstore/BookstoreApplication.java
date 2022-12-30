package com.OJTProject.bookstore;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.OJTProject.bookstore.entity.User;
import com.OJTProject.bookstore.entity.UserRole;
import com.OJTProject.bookstore.entity.UserStatus;
import com.OJTProject.bookstore.service.StorageService;
import com.OJTProject.bookstore.service.UserService;
import com.OJTProject.bookstore.utility.SecurityUtility;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;

	@Value("${custom.delete.images}")
	private String deleteImages;

	@Override
	public void run(String... args) throws Exception {

		if (ddlMode.equals("create")) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setPassword(SecurityUtility.passwordEncoder().encode("1111"));
			user.setUsername("admin");
			user.setUserRole(UserRole.admin);
			user.setUserStatus(UserStatus.active);
			user.setCreatedAt(LocalDateTime.now());
			userService.save(user);
		}

		if (deleteImages.equals("true")) {
			storageService.clearAll();
		}

	}

}
