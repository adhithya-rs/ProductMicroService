package com.vimal.product.customException;

public class DuplicateSaveException extends RuntimeException{
    public DuplicateSaveException(String message){
        super(message);
    }
}
