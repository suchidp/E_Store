package com.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
