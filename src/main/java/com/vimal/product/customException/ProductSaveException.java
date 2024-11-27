package com.vimal.product.customException;

public class ProductSaveException extends RuntimeException {
    public ProductSaveException(String message) {
        super(message);
    }
}