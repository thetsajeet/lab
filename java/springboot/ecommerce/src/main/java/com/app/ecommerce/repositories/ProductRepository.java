package com.app.ecommerce.repositories;

import org.springframework.stereotype.Repository;

import com.app.ecommerce.models.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional query methods can be defined here if needed
    public List<Product> findByIsActiveTrue();

    @Query("SELECT p FROM products p WHERE p.isActive = true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public List<Product> searchProductsByKeyword(@Param("keyword") String keyword);
}
