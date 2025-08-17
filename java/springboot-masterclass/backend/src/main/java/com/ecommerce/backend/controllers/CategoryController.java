package com.ecommerce.backend.controllers;

import com.ecommerce.backend.DTOs.CategoryDTO;
import com.ecommerce.backend.DTOs.CategoryResponseDTO;
import com.ecommerce.backend.config.AppConstants;
import com.ecommerce.backend.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponseDTO> getAllCategories(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.CATEGORY_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.CATEGORY_SORT_DIRECTION) String sortDirection) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDirection));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO c) {
        return new ResponseEntity<>(categoryService.createCategory(c), HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO c) {
        return new ResponseEntity<>(categoryService.updateCategory(id, c), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.ACCEPTED);
    }
}
