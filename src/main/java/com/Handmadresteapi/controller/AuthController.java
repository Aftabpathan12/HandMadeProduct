package com.Handmadresteapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Handmadresteapi.Dto.LoginDto;
import com.Handmadresteapi.Dto.LoginResponse;
import com.Handmadresteapi.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDto loginDto,HttpSession session){
	try {
		// Authenticate the user
		String message = authService.loginUser(loginDto.getUsernameOrEmail(),loginDto.getPassword());
		return ResponseEntity.ok(new LoginResponse(message));
	}catch (IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(new LoginResponse("Login failed:"+e.getMessage()));
	}
}
}
