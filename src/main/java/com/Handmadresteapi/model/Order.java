package com.Handmadresteapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

   
    private double totalPrice;  // Total price of the order

    
    private String addressline1;
    public String getRecivername() {
		return recivername;
	}

	public void setRecivername(String recivername) {
		this.recivername = recivername;
	}

	public String getRecieveremail() {
		return recieveremail;
	}

	public void setRecieveremail(String recieveremail) {
		this.recieveremail = recieveremail;
	}

	private String City;
    private String State;
    private String Country;
    private int pincode;
    private String recivername;
    private String recieveremail;
    private Long recieverphone;
    
    public Long getRecieverphone() {
		return recieverphone;
	}

	public void setRecieverphone(long recieverphone) {
		this.recieverphone = recieverphone;
	}

	public String getAddressline1() {
		return addressline1;
	}

	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	@Enumerated(EnumType.STRING)
    private OrderStatus status;  // Order status (e.g., PENDING, COMPLETED, CANCELLED)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

enum OrderStatus {
    PENDING,
    COMPLETED,
    CANCELLED
}
