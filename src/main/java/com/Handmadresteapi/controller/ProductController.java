package com.Handmadresteapi.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.repository.ProductRepository;
import com.Handmadresteapi.service.ProductService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/product")
public class ProductController {


		@Autowired
		private ProductService productserviceobj;
		
		@Autowired
		private ProductRepository productrepository;
		
		@GetMapping("/search")
		public String productsearch(@RequestParam String searchdata, Model model, HttpSession session) {
		    List<Product> productobj = productrepository.findByDescriptionOrName(searchdata, searchdata);
		    
		    // Make sure these attribute names match what your Dashboard expects
		    model.addAttribute("products", productobj); // or "product" depending on your Dashboard
		    model.addAttribute("username", session.getAttribute("loggedInUser"));
		    
		    return "Dashboard"; // Return to your existing Dashboard
		}
		
	    @PostMapping("/add")
	    public ResponseEntity<String> addproduct(@RequestPart("product") Product productobj, 
	                                         @RequestPart("file") MultipartFile file) {
	        String message = productserviceobj.saveproduct(productobj, file);
	        return ResponseEntity.ok(message);
	    }
		@GetMapping("/fetchdata")
		public List<Product> fetchdata()
		{
			return productserviceobj.fetchdata();
		}
		
		@PutMapping("updatedata/{id}")
		public String updatedata(@PathVariable int id,@RequestBody Product productobj)
		{
			productserviceobj.updatedata(id, productobj);
			return "Data updated";
		}
		
		
		@DeleteMapping("/deletedata/{id}")
		public String deletedata(@PathVariable int id)
		{
			
			productserviceobj.deletedata(id);
			return "Data Deleted";
		}
		 
	  	
		
		@GetMapping("/getpage")
	    public Page<Product> getProduct(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction
	    ) {
	        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	        return productrepository.findAll(PageRequest.of(page, size, sort));
	    }
		
		
	}

