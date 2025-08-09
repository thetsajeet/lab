package com.app.ecommerce.services;

import org.springframework.stereotype.Service;

import com.app.ecommerce.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
}
