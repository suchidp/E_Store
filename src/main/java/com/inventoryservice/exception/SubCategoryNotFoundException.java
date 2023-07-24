package com.inventoryservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * The custom Exception used to  check   an SubCategory is present or not   .
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubCategoryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SubCategoryNotFoundException(String message) {
        super(message);
    }
}
