package com.Handmadresteapi.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

	@NotBlank(message = "Username is required")
    @Size(min = 3,max =20, message="username must be detween 3 and 20 chracters")
	private String username;
	
	
	@Email(message="Email is required")
	@NotBlank(message = "Email shoul be valid")
	private String email;
	
	@NotBlank(message = "password is required")
	@Size(min = 3,max =20, message="password must be at least 6 chracters")
	private String password;
	
	public UserDto() {
		super();
	}


	public UserDto(
			@NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "username must be detween 3 and 20 chracters") String username,
			@Email(message = "Email is required") @NotBlank(message = "Email shoul be valid") String email,
			@NotBlank(message = "password is required") @Size(min = 3, max = 20, message = "password must be at least 6 chracters") String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

