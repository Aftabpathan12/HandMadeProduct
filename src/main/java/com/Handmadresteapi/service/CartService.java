package com.Handmadresteapi.service;

import com.Handmadresteapi.model.Cart;
import com.Handmadresteapi.model.CartItem;
import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.repository.CartRepository;
import com.Handmadresteapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    // Add item to cart
    public void addItemToCart(Integer userId, Integer productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(userService.getUserById(userId)); // Get user by id
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);

        // Update product quantity
        product.setQuantity(product.getQuantity() - 1);

        if (cart.getItems() == null) {
            ArrayList<CartItem> items = new ArrayList<>();
            items.add(item);
            cart.setItems(items);
        } else {
            cart.getItems().add(item);
        }

        cartRepository.save(cart);
    }

    // Get the cart by user ID
    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    // Remove item from cart
    public void removeItemFromCart(Integer userId, Integer itemId) {
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> items = cart.getItems();

        if (items != null) {
            items.removeIf(item -> item.getId().equals(itemId)); // Remove item by ID
            cartRepository.save(cart); // Save the updated cart
        }
    }

    public String getWelcomeMessage() {
        return "Welcome to the Cart Dashboard!";
    }
}
