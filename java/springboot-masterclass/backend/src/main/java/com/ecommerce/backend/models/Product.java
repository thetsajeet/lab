package com.ecommerce.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String productName;
    private String description;
    private String image;
    private int quantity;
    private double price;
    private double specialPrice;
    @PositiveOrZero(message = "Discount must be zero or positive")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;
}
