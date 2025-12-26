package com.Handmadresteapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Handmadresteapi.model.Cart;
import com.Handmadresteapi.model.CartItem;
import com.Handmadresteapi.repository.CartRepository;
import com.Handmadresteapi.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartrepository;

    // Add item to cart
    @PostMapping("/add")
    public String addItemToCart(
            @RequestParam Integer userId,
            @RequestParam Integer productId,
            @RequestParam int quantity,
            Model model,
            HttpSession session) {

        cartService.addItemToCart(userId, productId, quantity);

        Cart cartobj = cartrepository.findByUserId(userId);

        model.addAttribute("cartitems", cartobj.getItems());
        model.addAttribute("userId", userId);
        model.addAttribute("username", session.getAttribute("loggedInUser"));

        return "Cart";
    }

    @PostMapping("/remove")
    public String removeItemFromCart(
            @RequestParam Integer itemId,
            @RequestParam Integer userId,
            Model model,
            HttpSession session) {

        // ✅ CORRECT ORDER
        cartService.removeItemFromCart(userId, itemId);

        Cart cart = cartService.getCartByUserId(userId);

        model.addAttribute("cartitems", cart != null ? cart.getItems() : new ArrayList<>());
        model.addAttribute("userId", userId);
        model.addAttribute("username", session.getAttribute("loggedInUser"));

        return "redirect:/api/cart/view";
    }

}




