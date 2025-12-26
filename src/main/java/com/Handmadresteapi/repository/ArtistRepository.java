package com.Handmadresteapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Handmadresteapi.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // Find artist by email
    Optional<Artist> findByEmail(String email);

    // Check if an email is already in use
    boolean existsByEmail(String email);
}