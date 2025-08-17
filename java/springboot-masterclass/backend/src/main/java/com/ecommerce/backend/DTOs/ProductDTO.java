package com.ecommerce.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private String image;
    private int quantity;
    private double price;
    private double specialPrice;
    private double discount;
}
