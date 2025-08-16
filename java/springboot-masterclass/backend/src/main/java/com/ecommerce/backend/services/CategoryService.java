package com.ecommerce.backend.services;

import com.ecommerce.backend.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final List<Category> categories = new ArrayList<>();
    private static Long id = 0L;

    public List<Category> getAllCategories() {
        return categories;
    }

    public String createCategory(Category c) {
        c.setId(id++);
        this.categories.add(c);

        return "success";
    }

    public String deleteCategory(Long id) {
        Category category = categories
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        categories.remove(category);
        return "deleted";
    }

    public String updateCategory(Long id, Category category) {
        Category cat = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        cat.setCategoryName(category.getCategoryName());
        return "updated";
    }
}
