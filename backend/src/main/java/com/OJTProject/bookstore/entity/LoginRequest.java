package com.OJTProject.bookstore.entity;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Required")
	private String email;

	@NotBlank(message = "Required")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
