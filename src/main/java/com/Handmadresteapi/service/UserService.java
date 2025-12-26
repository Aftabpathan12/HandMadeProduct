package com.Handmadresteapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Handmadresteapi.Dto.UserDto;
import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.model.User;
import com.Handmadresteapi.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

    public User registerUser(UserDto userDto) {
        
//    	if (userRepository.existsByEmail(userDto.getEmail())) {
//            throw new IllegalArgumentException("Email already in use");
//        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // Consider hashing the password
        return userRepository.save(user);
    }
        
    // Find user by username or email (for login)
    public User findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

	public User getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	    
	}

	
    
    }
