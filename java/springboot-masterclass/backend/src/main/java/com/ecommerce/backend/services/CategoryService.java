package com.ecommerce.backend.services;

import com.ecommerce.backend.models.Category;
import com.ecommerce.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public String createCategory(Category c) {
        categoryRepository.save(c);
        return "success";
    }

    public String deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository
                .findById(id);

        if(optionalCategory.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");

        categoryRepository.delete(optionalCategory.get());

        return "deleted";
    }

    public String updateCategory(Long id, Category category) {
        Category cat = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        category.setId(cat.getId());
        categoryRepository.save(category);

        return "updated";
    }
}
