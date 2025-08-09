package com.app.ecommerce.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.CartItemRequest;
import com.app.ecommerce.dto.CartItemResponse;
import com.app.ecommerce.models.CartItem;
import com.app.ecommerce.models.Product;
import com.app.ecommerce.models.User;
import com.app.ecommerce.repositories.CartItemRepository;
import com.app.ecommerce.repositories.ProductRepository;
import com.app.ecommerce.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public boolean addToCart(Long userId, CartItemRequest cartItemRequest) {

        // Logic to add a cart item
        // This is just a placeholder; actual implementation will involve saving to the database
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if(productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();
        if(product.getStockQuantity() < cartItemRequest.getQuantity()) {
            return false; // Not enough stock
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            return false; // User not found
        }

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(existingCartItem != null) {
            // Update existing cart item
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            newCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepository.save(newCartItem);
        }

        return true;
    }

    public boolean removeFromCart(Long userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()) {
            return false;
        }
        
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            return false; // User not found
        }

        User user = userOpt.get();
        Product product = productOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(existingCartItem == null) {
            return false; // Item not found in cart
        }
        
        cartItemRepository.deleteByUserAndProduct(user, product);
        return true;
    }

    public List<CartItem> getCart(Long userId) {
        return userRepository.findById(userId).map(cartItemRepository::findByUser).orElseGet(List::of);
    }
}
