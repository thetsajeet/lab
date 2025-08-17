package com.ecommerce.backend.controllers;

import com.ecommerce.backend.DTOs.ProductDTO;
import com.ecommerce.backend.DTOs.ProductResponseDTO;
import com.ecommerce.backend.config.AppConstants;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> createProduct(
            @PathVariable Long categoryId,
            @Valid @RequestBody Product product
    ) {
        return new ResponseEntity<>(
                productService.createProduct(categoryId, product),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_DIRECTION) String sortDirection
    ) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_DIRECTION) String sortDirection,
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(productService.getAllProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponseDTO> getAllProducts(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.PRODUCT_SORT_DIRECTION) String sortDirection,
            @PathVariable String keyword
    ) {
        return ResponseEntity.ok(productService.getAllProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortDirection));
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody Product product
    ) {
        return ResponseEntity.ok(productService.updateProduct(productId, product));
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(
            @PathVariable Long productId,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(productService.updateProductImage(productId, file));
    }

}
