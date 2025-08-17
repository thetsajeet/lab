package com.ecommerce.backend.services;

import com.ecommerce.backend.DTOs.CategoryDTO;
import com.ecommerce.backend.DTOs.CategoryResponseDTO;
import com.ecommerce.backend.exceptions.APIException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.Category;
import com.ecommerce.backend.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sortByAndOrder = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(page);
        List<Category> categories = categoryPage.getContent();
        List<CategoryDTO> categoryDTOS = categories
                .stream()
                .map(cat -> modelMapper.map(cat, CategoryDTO.class))
                .toList();

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setPageNumber(categoryPage.getNumber());
        categoryResponseDTO.setPageSize(categoryPage.getSize());
        categoryResponseDTO.setTotalElements(categoryPage.getTotalElements());
        categoryResponseDTO.setTotalPages(categoryPage.getTotalPages());
        categoryResponseDTO.setLastPage(categoryPage.isLast());
        categoryResponseDTO.setContent(categoryDTOS);

        return categoryResponseDTO;
    }

    public CategoryDTO createCategory(CategoryDTO c) {
        if (categoryRepository
                .findByCategoryName(c.getCategoryName())
                .isPresent())
            throw new APIException("Category with name: " + c.getCategoryName() + " already exists.");
        Category category = modelMapper.map(c, Category.class);
        Category cat = categoryRepository.save(category);
        return modelMapper.map(cat, CategoryDTO.class);
    }

    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository
                .findById(id);

        if (optionalCategory.isEmpty())
            throw new ResourceNotFoundException("Category", "id", id);

        categoryRepository.delete(optionalCategory.get());
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO c) {
        Category cat = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        Category category = modelMapper.map(c, Category.class);
        category.setCategoryId(cat.getCategoryId());
        cat = categoryRepository.save(category);

        return modelMapper.map(cat, CategoryDTO.class);
    }
}
