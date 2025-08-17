package com.ecommerce.backend.services;

import com.ecommerce.backend.DTOs.ProductDTO;
import com.ecommerce.backend.DTOs.ProductResponseDTO;
import com.ecommerce.backend.exceptions.APIException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.Category;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.repositories.CategoryRepository;
import com.ecommerce.backend.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Value("${project.image_path}")
    private String imagePath;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO createProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        if (category.getProducts().stream().anyMatch(cat -> cat.getProductName().equals(product.getProductName())))
            throw new APIException("Product with name '" + product.getProductName() + "' already exists.");

        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public ProductResponseDTO getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sortByAndOrder = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> productPage = productRepository.findAll(page);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products
                .stream()
                .map(prod -> modelMapper.map(prod, ProductDTO.class))
                .toList();

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setContent(productDTOs);
        productResponseDTO.setPageNumber(productPage.getNumber());
        productResponseDTO.setPageSize(productPage.getSize());
        productResponseDTO.setTotalElements(productPage.getTotalElements());
        productResponseDTO.setTotalPages(productPage.getTotalPages());
        productResponseDTO.setLastPage(productPage.isLast());

        return productResponseDTO;
    }

    public ProductResponseDTO getAllProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Sort sortByAndOrder = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> productPage = productRepository.findByCategory(category, page);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products
                .stream()
                .map(prod -> modelMapper.map(prod, ProductDTO.class))
                .toList();

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setContent(productDTOs);
        productResponseDTO.setPageNumber(productPage.getNumber());
        productResponseDTO.setPageSize(productPage.getSize());
        productResponseDTO.setTotalElements(productPage.getTotalElements());
        productResponseDTO.setTotalPages(productPage.getTotalPages());
        productResponseDTO.setLastPage(productPage.isLast());

        return productResponseDTO;
    }

    public ProductResponseDTO getAllProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sortByAndOrder = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%", page);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products
                .stream()
                .map(prod -> modelMapper.map(prod, ProductDTO.class))
                .toList();

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setContent(productDTOs);
        productResponseDTO.setPageNumber(productPage.getNumber());
        productResponseDTO.setPageSize(productPage.getSize());
        productResponseDTO.setTotalElements(productPage.getTotalElements());
        productResponseDTO.setTotalPages(productPage.getTotalPages());
        productResponseDTO.setLastPage(productPage.isLast());

        return productResponseDTO;
    }

    public ProductDTO updateProduct(Long productId, Product product) {
        Optional<Product> optionalProduct = productRepository
                .findById(productId);

        if (optionalProduct.isEmpty())
            throw new ResourceNotFoundException("Product", "id", productId);

        product.setProductId(optionalProduct.get().getProductId());
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository
                .findById(productId);

        if (product.isEmpty())
            throw new ResourceNotFoundException("Product", "id", productId);

        productRepository.delete(product.get());
    }

    public ProductDTO updateProductImage(Long productId, MultipartFile file) throws IOException {
        Optional<Product> optionalProduct = productRepository
                .findById(productId);

        if (optionalProduct.isEmpty())
            throw new ResourceNotFoundException("Product", "id", productId);

        Product product = optionalProduct.get();
        String filename = fileService.uploadFile(imagePath, file);
        product.setImage(filename);
        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
}
