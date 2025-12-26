package com.Handmadresteapi.Dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ArtistRegistrationDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Number is required")
    @Digits(integer = 10, fraction = 0, message = "Number must be a 10-digit valid phone number")
    private Long number;

    @NotNull(message = "Adhar number is required")
    @Digits(integer = 12, fraction = 0, message = "Adhar number must be a 12-digit valid number")
    private Long adhar;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getAdhar() {
		return adhar;
	}

	public void setAdhar(Long adhar) {
		this.adhar = adhar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
}

