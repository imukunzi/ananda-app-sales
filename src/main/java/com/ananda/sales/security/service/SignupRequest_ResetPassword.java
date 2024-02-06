package com.ananda.sales.security.service;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class SignupRequest_ResetPassword {

	@Email
	@Size(min = 1, max = 1000)
	private String username;

	// @NotBlank
	@Size(min = 6, max = 400)
	private String password;

	@Size(min = 1, max = 1000)
	private String token;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
