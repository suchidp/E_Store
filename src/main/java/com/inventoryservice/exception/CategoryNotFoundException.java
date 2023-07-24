package com.inventoryservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * The custom Exception used to  check   an Category is present or not   .
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
