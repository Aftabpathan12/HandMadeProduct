package com.Handmadresteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Handmadresteapi.model.Cart;
import com.Handmadresteapi.model.CartItem;
import com.Handmadresteapi.model.Order;
import com.Handmadresteapi.model.User;
import com.Handmadresteapi.repository.CartRepository;
import com.Handmadresteapi.repository.UserRepository;
import com.Handmadresteapi.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    // ================= PLACE ORDER =================
    @PostMapping("/place")
    public String placeOrder(
            HttpSession session,
            Model model,
            @RequestParam Integer userId,
            @RequestParam String recievercity,
            @RequestParam String recievercountry,
            @RequestParam String recievername,
            @RequestParam String recieveremail,
            @RequestParam String recieverphone,
            @RequestParam String recieveradd,
            @RequestParam String recieverpincode,
            @RequestParam String recieverstate) {

        Order order = orderService.placeOrder(
                userId,
                recievercity,
                recieverstate,
                recievercountry,
                recievername,
                recieveremail,
                recieveradd,
                recieverpincode,
                recieverphone
        );

        model.addAttribute("orderid", order.getId());
        model.addAttribute("username", session.getAttribute("loggedInUser"));
        model.addAttribute("orderamount", order.getTotalPrice());

        return "Payment";
    }

    // ================= ADD ADDRESS PAGE =================
    @GetMapping("/addaddress")
    public String showAddAddressForm(HttpSession session, Model model) {

        String sessionUser = (String) session.getAttribute("loggedInUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsernameOrEmail(sessionUser, sessionUser);
        if (user == null) {
            return "redirect:/login";
        }

        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null || cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cart.getItems();

        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("user", user);
        model.addAttribute("username", user.getEmail());
        model.addAttribute("cartitems", cartItems);
        model.addAttribute("TotalAmount", totalAmount);

        return "AddAddress"; // AddAddress.html
    }
}
