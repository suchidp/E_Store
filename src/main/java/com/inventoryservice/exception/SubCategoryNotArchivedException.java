package com.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubCategoryNotArchivedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SubCategoryNotArchivedException(String message) {
        super(message);
    }
}
