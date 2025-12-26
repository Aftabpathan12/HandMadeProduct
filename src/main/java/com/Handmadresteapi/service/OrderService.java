package com.Handmadresteapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Handmadresteapi.model.Cart;
import com.Handmadresteapi.model.CartItem;
import com.Handmadresteapi.model.Order;
import com.Handmadresteapi.model.OrderItems;
import com.Handmadresteapi.repository.CartRepository;
import com.Handmadresteapi.repository.OrderItemsRepository;
import com.Handmadresteapi.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemsRepository orderitemrpository;

    @Autowired
    private JavaMailSender mailSender; // Inject JavaMailSender

    // Place order
    public Order placeOrder(Integer userId,String recievercity,String recieverstate,String recievercountry,String recievername,String recieveremail,String recieveradd,String recieverpincode,String recieverphone) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        System.out.println("1");
        Order order = new Order();
        order.setUser(cart.getUser());

        System.out.println("2");
        // Calculate total price
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        order.setTotalPrice(totalPrice);
        System.out.println("3");

        order.setCity(recievercity);
        order.setCountry(recievercountry);
        order.setState(recieverstate);
        order.setAddressline1(recieveradd);
        order.setPincode(Integer.parseInt(recieverpincode));
        order.setRecivername(recievername);
        order.setRecieveremail(recieveremail);
        order.setRecieverphone(Long.parseLong(recieverphone));
        
        
        // Save order
        orderRepository.save(order);
        OrderItems orderitems = new OrderItems();
        for (CartItem c : cart.getItems()) {
            orderitems.setOrderid(order);
            orderitems.setProduct(c.getProduct());
            orderitems.setQuantity(c.getQuantity());
            orderitemrpository.save(orderitems);
            System.out.println(orderitems.getProduct().getName());
        }

        // Clear the cart after placing the order
        cart.getItems().clear();
        
        cartRepository.delete(cart);

        // Send order confirmation email
        sendOrderConfirmationEmail(cart.getUser().getEmail(), order);

        return order;
    }

    // Send order confirmation email
    private void sendOrderConfirmationEmail(String recipientEmail, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Order Confirmation");
        message.setText("Thank you for your order!\n\nOrder Details:\n" +
                "Order ID: " + order.getId() + "\n" +
                "Total Price: $" + order.getTotalPrice() + "\n\n" +
                "Your order will be processed soon!");

        try {
            mailSender.send(message);
            System.out.println("Order confirmation email sent to " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        
    }
        public List<Order> getOrdersByUserId(Long userId) {
            return orderRepository.findByUserId(userId);
        }
       
}
