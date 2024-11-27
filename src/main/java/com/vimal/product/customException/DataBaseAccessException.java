package com.vimal.product.customException;

public class DataBaseAccessException extends RuntimeException {
    public DataBaseAccessException(String message) {
        super(message);
    }
}
