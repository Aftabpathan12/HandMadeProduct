package com.Handmadresteapi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.repository.ArtistRepository;
import com.Handmadresteapi.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/artist/product")
public class ArtishDashboardController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ArtistRepository artistrepoobj;

    private static final String UPLOAD_DIR = "C:/Users/dell/Documents/workspace-spring-tool-suite-4-4.26.0.RELEASE/HandMadeProject1/src/main/resources/static/images/";

    @PostMapping("/add")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam int price,
            @RequestParam int quantity,
            @RequestParam MultipartFile file,HttpSession session) {

        try {
            // Ensure the directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the file in the specified directory
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, file.getBytes());

            // Create and populate the Product object
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setImagePath(fileName);
            product.setArtistid(artistrepoobj.findByEmail((String)session.getAttribute("LoggedInUser")).get().getId());
            // Save the product to the database
            productRepository.save(product);

        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // Return an error page if file upload fails
        }

        return "redirect:/artist/product/list"; // Redirect to the product list page
    }
    
    @GetMapping("/list")
    public String listProducts(Model model,HttpSession session ) {
        model.addAttribute("products", productRepository.findByArtistid(artistrepoobj.findByEmail((String)session.getAttribute("LoggedInUser")).get().getId()));
        return "productList";
        // This should match your Thymeleaf template name
    }
    
    @GetMapping("/ArtistDashboard")
    public String showArtistDashboard(Model model,HttpSession session) {
        model.addAttribute("username", "Artist Name");  // Example attribute
        return "ArtistDashboard";  // The name of the Thymeleaf template
    }
    
    // New Search Controller
    @GetMapping("/search")
    public String searchProducts(@RequestParam String query, Model model, HttpSession session) {
        Long artistId = artistrepoobj.findByEmail((String) session.getAttribute("LoggedInUser")).get().getId();
        model.addAttribute("products", productRepository.searchByNameAndArtistId(query, artistId));
        return "productList"; // Use the same template for listing products
    }
}

   
