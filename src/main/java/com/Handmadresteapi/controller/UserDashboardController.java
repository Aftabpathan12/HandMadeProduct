package com.Handmadresteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.model.User;
import com.Handmadresteapi.service.ProductService;
import com.Handmadresteapi.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserDashboardController {
	 
	@Autowired
	private ProductService productserviceobj;

	 @Autowired
	 private UserService userservice;
	
	 @GetMapping("/Dashboard")
     String dashboard(HttpSession session, Model model) {
     String loggedInUser = (String) session.getAttribute("loggedInUser");

     if (loggedInUser == null) {
         return "redirect:/login?error=Please+login+first";
     }
     User user = userservice.findByUsernameOrEmail(loggedInUser);
     model.addAttribute("username", loggedInUser);
     
     List<Product> products = productserviceobj.fetchdata();
     model.addAttribute("products",products );
     int userid =user.getId().intValue();
     
     model.addAttribute("userID",userid);
     return "Dashboard"; // Points to dashboard.html
 }

 // Logout user
 @GetMapping("/logout")
 public String logout(HttpSession session) {
     session.invalidate(); // Invalidate the session
     return "redirect:/login"; // Redirect to login page
 }

 @GetMapping("/contact")
 public String contactPage(Model model,HttpSession session) {
 	
 	// Retrieve user email from session
     String loggedInUserEmail = (String) session.getAttribute("loggedInUserEmail");
     model.addAttribute("email", loggedInUserEmail != null ? loggedInUserEmail : "Guest");
     return "Contact"; // Returns the view named "contact.html"
 }
}
