package com.vimal.product.services;

import com.vimal.product.customException.DataBaseAccessException;
import com.vimal.product.customException.DuplicateSaveException;
import com.vimal.product.customException.ProductNotFoundException;
import com.vimal.product.customException.ProductSaveException;
import com.vimal.product.dtos.CreateProductRequestDto;
import com.vimal.product.models.Category;
import com.vimal.product.models.Product;
import com.vimal.product.projections.ProductTitleProjection;
import com.vimal.product.repositories.CategoryRepository;
import com.vimal.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductTitleProjection> getProductTitles() {
        try {
            System.out.println("Start Test");
            Optional<List<ProductTitleProjection>> productTitleProjections = productRepository.getTitles();
            System.out.println("Test");
            if (productTitleProjections.isPresent() && productTitleProjections.get().isEmpty()) {
                throw new ProductNotFoundException("No Products present");
            }
            return productTitleProjections.orElseThrow(() -> new ProductNotFoundException("No Products present"));
        }catch (DataAccessException ex) {
            throw new DataBaseAccessException("Failed to access the database: ");
        }

    }


    @Override
    public List<Product> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new ProductNotFoundException("No Products present in repository");
            }
            return products;
        } catch (DataAccessException ex) {
            throw new DataBaseAccessException("Failed to access the database: ");
        }
    }

    @Override
    public Product getProductById(long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new ProductNotFoundException("Product with id " + id + " is not found in the database");
            }
            return product.get();
        } catch (DataAccessException ex) {
            throw new DataBaseAccessException("Failed to access the database while retrieving product by ID: ");
        }
    }

    @Override
    public Product addProduct(String title,
                                 String description,
                                 double price,
                                 String image,
                                 String category) {

        Product p = convertToProduct(title, description, price, image, category);

        Product createdProduct;
        try {
            createdProduct = productRepository.save(p);
            return createdProduct;
        } catch (DataAccessException ex) { // Catch Spring's generic data access exceptions
            throw new ProductSaveException("Failed to save product due to data access issue");
        }

    }

    @Override
    @Transactional
    public List<Product> createManyProduct(List<CreateProductRequestDto> createProductRequestDtos) {
        List<Product> products = new ArrayList<>();

        for (CreateProductRequestDto dto : createProductRequestDtos) {
            // Convert each DTO to a Product entity
            Product product = convertToProduct(dto.getTitle(),
                    dto.getDescription(),
                    dto.getPrice(),
                    dto.getImage(),
                    dto.getCategory());
            products.add(product);
        }

        List<Product> createdProducts;
        try {
            // Save all products at once
            createdProducts = productRepository.saveAll(products);
        } catch (DataAccessException ex) { // Catch Spring's generic data access exceptions
            throw new ProductSaveException("Failed to save products due to data access issue");
        }
        return createdProducts;
    }

    private Product convertToProduct(String title,
                                     String description,
                                     double price,
                                     String image,
                                     String category) {

        Optional<Product> optionalProduct = productRepository.findByTitle(title);
        if(optionalProduct.isPresent()){
            throw new DuplicateSaveException("The product with title " + title + " already exists");
        }
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);

        // Handling the category
        Category category1 = categoryRepository.findByTitle(category)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setTitle(category);
                    return categoryRepository.save(newCategory);
                });

        product.setCategory(category1);
        return product;
    }
}