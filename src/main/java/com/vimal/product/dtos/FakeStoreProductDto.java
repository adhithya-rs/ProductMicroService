package com.vimal.product.dtos;

import com.vimal.product.models.Category;
import com.vimal.product.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;


    public Product toProduct(){
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
        Category category1 = new Category();
        category1.setTitle(category);
        product.setCategory(category1);

        return product;
    }
}
