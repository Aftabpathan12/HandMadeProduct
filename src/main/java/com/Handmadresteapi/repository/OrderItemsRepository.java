package com.Handmadresteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Handmadresteapi.model.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer>{

}
