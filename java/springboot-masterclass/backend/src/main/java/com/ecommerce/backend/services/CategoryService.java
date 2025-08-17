package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.APIException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.Category;
import com.ecommerce.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (categoryRepository
                .findByCategoryName(c.getCategoryName())
                .isPresent())
            throw new APIException("Category with name: " + c.getCategoryName() + " already exists.");

        categoryRepository.save(c);
        return "success";
    }

    public String deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository
                .findById(id);

        if (optionalCategory.isEmpty())
            throw new ResourceNotFoundException("Category", "id", id);

        categoryRepository.delete(optionalCategory.get());

        return "deleted";
    }

    public String updateCategory(Long id, Category category) {
        Category cat = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setId(cat.getId());
        categoryRepository.save(category);

        return "updated";
    }
}
