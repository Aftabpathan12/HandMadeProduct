package com.Handmadresteapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Handmadresteapi.Dto.ArtistLoginDto;
import com.Handmadresteapi.Dto.ArtistRegistrationDTO;
import com.Handmadresteapi.model.Artist;
import com.Handmadresteapi.repository.ArtistRepository;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public Artist registerArtist(ArtistRegistrationDTO dto) {
        if (artistRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setEmail(dto.getEmail());
        artist.setNumber(dto.getNumber());
        artist.setAdhar(dto.getAdhar());
        artist.setPassword(dto.getPassword()); // Store plain text password

        return artistRepository.save(artist);
    }

    public boolean login(ArtistLoginDto dto) {
        Artist artist = artistRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        return dto.getPassword().equals(artist.getPassword()); // Compare plain text password
    }
    public Long loginAndGetId(ArtistLoginDto dto) {
        boolean isAuthenticated = login(dto); // Assume this checks the credentials
        if (isAuthenticated) {
            return artistRepository.findByEmail(dto.getEmail())
                                   .map(Artist::getId)
                                   .orElse(null);
        }
        return null;
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }


    
    
}


