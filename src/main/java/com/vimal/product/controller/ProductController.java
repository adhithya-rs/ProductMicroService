package com.vimal.product.controller;

import com.vimal.product.dtos.CreateProductRequestDto;
import com.vimal.product.models.Product;
import com.vimal.product.projections.ProductTitleProjection;
import com.vimal.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {
    @Autowired
    @Qualifier("selfProductService")
    private ProductService productService;


    @GetMapping("/products")
    public List<Product> getAllProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") long id) {



        Product p= productService.getProductById(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductRequestDto createProductRequestDto){
        Product p = productService.addProduct(createProductRequestDto.getTitle(),
                createProductRequestDto.getDescription(),
                createProductRequestDto.getPrice(),
                createProductRequestDto.getImage(),
                createProductRequestDto.getCategory());

        return new ResponseEntity<>(p, HttpStatus.CREATED);

    }

    @PostMapping("/products/batch")
    public ResponseEntity<List<Product>> createManyProduct(@RequestBody List<CreateProductRequestDto> createProductRequestDtos){

        List<Product> savedProducts = productService.createManyProduct(createProductRequestDtos);
        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }

    @GetMapping("/products/title")
    public ResponseEntity<List<ProductTitleProjection>> getProductTitles(){
        List<ProductTitleProjection> productTitleProjections= productService.getProductTitles();
        return new ResponseEntity<>(productTitleProjections, HttpStatus.OK);
    }
}
