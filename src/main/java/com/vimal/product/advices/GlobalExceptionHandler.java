package com.vimal.product.advices;

import com.vimal.product.customException.DataBaseAccessException;
import com.vimal.product.customException.DuplicateSaveException;
import com.vimal.product.customException.ProductNotFoundException;
import com.vimal.product.customException.ProductSaveException;
import com.vimal.product.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(productNotFoundException.getMessage());

        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        return responseEntity;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleServerException(Exception exception) {
        ErrorDTO errorDTO = new ErrorDTO("An unexpected error occurred.");

        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
    @ExceptionHandler(ProductSaveException.class)
    public ResponseEntity<ErrorDTO> handleProductSaveException(ProductSaveException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DataBaseAccessException.class)
    public ResponseEntity<ErrorDTO> handleDataBaseAccessException(DataBaseAccessException ex) {
        // Log the exception details for debugging purposes
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());

        // Return a ResponseEntity object containing the error details and the HTTP status
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DuplicateSaveException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateSaveException(DuplicateSaveException ex) {
        // Log the exception details for debugging purposes
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());

        // Return a ResponseEntity object containing the error details and the HTTP status
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex) {
        // Log the exception details for debugging purposes
        ErrorDTO errorDTO = new ErrorDTO("The parameter in endpoint is invalid");

        // Return a ResponseEntity object containing the error details and the HTTP status
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDTO> handleEndpointNotPresentException(NoResourceFoundException ex) {
        // Log the exception details for debugging purposes
        ErrorDTO errorDTO = new ErrorDTO("Page not found / The requested endpoint is not available.");

        // Return a ResponseEntity object containing the error details and the HTTP status
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
