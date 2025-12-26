package com.Handmadresteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Handmadresteapi.Dto.ArtistLoginDto;
import com.Handmadresteapi.Dto.ArtistRegistrationDTO;
import com.Handmadresteapi.model.Artist;
import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.service.ArtistService;
import com.Handmadresteapi.service.ProductService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ProductService productService;

    // Show the registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("artist", new ArtistRegistrationDTO());
        return "ArtistRistration"; // Thymeleaf template for registration
    }

    // Handle the registration form submission
    @PostMapping("/register")
    public String registerArtist(@ModelAttribute("artist") @Valid ArtistRegistrationDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "ArtistRistration";
        }
        try {
            artistService.registerArtist(dto);
            return "redirect:/auth/login"; // Redirect to login after successful registration
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.artist", e.getMessage());
            return "ArtistRistration";
        }
    }

    // Show the login form without artist ID
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new ArtistLoginDto());
        return "ArtistLogin"; // Thymeleaf template for login
    }

    // Handle login submission
    @PostMapping("/login")
    public String loginArtist(@ModelAttribute("login") @Valid ArtistLoginDto dto, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "ArtistLogin";
        }

        Long artistId = artistService.loginAndGetId(dto);
        if (artistId != null) {
            Artist artist = artistService.getArtistById(artistId);
            // Store artist info in session
            session.setAttribute("LoggedInUser", artist.getEmail());
            session.setAttribute("LoggedInUserName", artist.getName());
            session.setAttribute("LoggedInUserId", artist.getId());

            return "redirect:/auth/artist/dashboard"; // Redirect to the dashboard
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "ArtistLogin";
        }
    }

    @GetMapping("/artist/dashboard")
    public String getDashboard(Model model, HttpSession session) {
        // Add session values to the model
        model.addAttribute("username", session.getAttribute("LoggedInUserName"));
        model.addAttribute("email", session.getAttribute("LoggedInUser"));
        model.addAttribute("userId", session.getAttribute("LoggedInUserId"));

        // Fetch products for the logged-in artist
        Long artistId = (Long) session.getAttribute("LoggedInUserId");
        List<Product> products = productService.getProductsByArtistId(artistId);
        model.addAttribute("product", products);

        return "artistDashboard"; // View name
    }
    
    @GetMapping("/artist/ArtistDashboard")
    public String showArtistDashboard(Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("LoggedInUserName"));
        model.addAttribute("email", session.getAttribute("LoggedInUser"));
        model.addAttribute("userId", session.getAttribute("LoggedInUserId"));

        // Fetch products for the logged-in artist
        Long artistId = (Long) session.getAttribute("LoggedInUserId");
        List<Product> products = productService.getProductsByArtistId(artistId);
        model.addAttribute("product", products);

        return "artistDashboard"; // Matches artistDashboard.html
    }
}


