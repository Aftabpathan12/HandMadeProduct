package com.Handmadresteapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Handmadresteapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);
	List<Order> findByUserId(Integer userId);

}
