package com.Handmadresteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Handmadresteapi.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Integer userId);
    Cart findByUserId(Long userid);
   
    
}
