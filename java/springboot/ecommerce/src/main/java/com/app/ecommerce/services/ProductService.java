package com.app.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.ProductRequest;
import com.app.ecommerce.dto.ProductResponse;
import com.app.ecommerce.models.Product;
import com.app.ecommerce.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    // This service will contain methods to handle product-related business logic
    // For example, creating a product, updating a product, fetching products, etc.

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        // Logic to create a product from the request and return a response
        // This is just a placeholder; actual implementation will involve saving to the database
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        // Logic to update a product by ID
        // This is just a placeholder; actual implementation will involve fetching from the database
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, productRequest);
            Product savedProduct = productRepository.save(existingProduct);
            return mapToProductResponse(savedProduct); 
        });
    }

    public List<ProductResponse> getAllProducts() {
        // Logic to fetch all products and return a list of responses
        // This is just a placeholder; actual implementation will involve fetching from the database
        return productRepository.findByIsActiveTrue()
            .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public boolean deleteProduct(Long id) {
        // Logic to delete a product by ID
        // This is just a placeholder; actual implementation will involve deleting from the database
        return productRepository.findById(id).map(product -> {
            product.setIsActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProductsByKeyword(keyword)
            .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public Optional<ProductResponse> getProduct(Long id) {
        return productRepository.findById(id).map(this::mapToProductResponse);
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setIsActive(product.getIsActive());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
    }
}
