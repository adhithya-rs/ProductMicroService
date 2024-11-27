package com.vimal.product.services;

import com.vimal.product.customException.ProductNotFoundException;
import com.vimal.product.dtos.CreateProductRequestDto;
import com.vimal.product.models.Product;
import com.vimal.product.projections.ProductTitleProjection;

import java.util.List;

public interface ProductService {


    List<Product> getAllProducts();

    Product getProductById(long id) throws ProductNotFoundException;

    Product addProduct(String title, String description, double price, String image, String category);

    List<Product> createManyProduct(List<CreateProductRequestDto> createProductRequestDtos);

    List<ProductTitleProjection> getProductTitles();
}
