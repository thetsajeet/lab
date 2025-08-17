package com.ecommerce.backend.repositories;

import com.ecommerce.backend.models.Category;
import com.ecommerce.backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods can be defined here if needed
    Page<Product> findByCategory(Category category, Pageable page);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable page);

    Optional<Product> findByProductName(String productName);
}
