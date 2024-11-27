package com.vimal.product.customException;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message){
        super(message);

    }
}
