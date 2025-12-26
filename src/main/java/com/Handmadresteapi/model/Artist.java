package com.Handmadresteapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "\"artist\"")
public class Artist {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
	@NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "rname must be between 3 and 20 characters")
	public String name;
	
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
	private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;



	@NotNull(message = "Number is required")
    @Digits(integer = 10, fraction = 0, message = "Number must be a 10-digit valid phone number")
    @Min(value = 1000000000, message = "Number must be at least 10 digits")
    private Long number;

    @NotNull(message = "Adhar number is required")
    @Digits(integer = 12, fraction = 0, message = "Adhar number must be a 12-digit valid number")
    @Min(value = 100000000000L, message = "Adhar number must be at least 12 digits")
    private Long adhar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	 public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
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
}
