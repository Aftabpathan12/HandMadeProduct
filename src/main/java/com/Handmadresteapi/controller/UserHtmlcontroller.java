package com.Handmadresteapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Handmadresteapi.Dto.UserDto;
import com.Handmadresteapi.model.User;
import com.Handmadresteapi.service.AuthService;
import com.Handmadresteapi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserHtmlcontroller {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String showRegistrationForm() {
	    return "UserRegistration"; // Points to classUserRegistration.html in templates
	}

	@PostMapping("/register")
	public String registerUser(
	    @RequestParam("username") String username,
	    @RequestParam("email") String email,
	    @RequestParam("password") String password) {

	    UserDto userDto = new UserDto();
	    userDto.setUsername(username);
	    userDto.setEmail(email);
	    userDto.setPassword(password);

	    User user = userService.registerUser(userDto);
	    return "UserLogin";
	}

    @Autowired
    private AuthService authService;

    // Display the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "UserLogin"; // Points to login.html in the templates directory
    }

    // Process the login form without using Model
    @PostMapping("/login")
    public String login(@RequestParam String usernameOrEmail, 
                        @RequestParam String password, 
                        @RequestParam(required = false) String error, 
                        HttpServletResponse response,HttpSession session) {
        try {
        	
            String message = authService.loginUser(usernameOrEmail, password);
            System.out.println(message);
            session.setAttribute("loggedInUser", usernameOrEmail);
            System.out.println(session.getAttribute("loggedInUser"));
            return "redirect:/Dashboard"; // Indicating that the response is already handled
        } catch (Exception e) {
            return "redirect:/Dashboard"; // Redirect to login page with error query
        }
    }

    // Display the login success page
    @GetMapping("/login-success")
    public String loginSuccess(@RequestParam String message, HttpServletRequest request) {
        request.setAttribute("successMessage", message);
        return "login-success"; // Points to login-success.html
    }
	
	
}
