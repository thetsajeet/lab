package com.app.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.dto.CartItemRequest;
import com.app.ecommerce.dto.CartItemResponse;
import com.app.ecommerce.models.CartItem;
import com.app.ecommerce.services.CartItemService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

   @PostMapping
   public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") Long userId, 
                                         @RequestBody CartItemRequest cartItemRequest) {
        if(!cartItemService.addToCart(userId, cartItemRequest)) 
            return ResponseEntity.badRequest().body("Product out of stock or user not found");

        return ResponseEntity.status(HttpStatus.CREATED).build();                                 
   }

   @DeleteMapping("/items/{productId}")
   public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") Long userId, 
                                         @PathVariable Long productId) {
                                            return cartItemService.removeFromCart(userId, productId) 
                                                ? ResponseEntity.noContent().build()
                                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in cart");
   }
    
   @GetMapping
   public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-ID") Long userId) {
        return ResponseEntity.ok(cartItemService.getCart(userId));
   }
   
}
