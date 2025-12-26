package com.Handmadresteapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Handmadresteapi.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

	List<Product>findByDescriptionOrName(String description,String name);
    
    List<Product> findByArtistid(Long artistid);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:query% AND p.artistid = :artistId")
    List<Product> searchByNameAndArtistId(String query, Long artistId);
    
    
	
}
