package com.Handmadresteapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Handmadresteapi.model.User;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	// Login user (without JWT, just returning message)

	public String loginUser (String usernameOrEmail, String password) {

	// Find user by username or email

	
	User user = userService.findByUsernameOrEmail(usernameOrEmail);

	if (user == null) {

	throw new IllegalArgumentException("User not found"); // Or throw a custom exception

	}

	// Check if the password matches (plain text comparison for simplicity)

	if (!user.getPassword().equals(password)) {

	throw new IllegalArgumentException("Invalid password");

	}

	// If login successful, return success message (or manage session if needed)

	return "Login successful!";

	
	}
}
