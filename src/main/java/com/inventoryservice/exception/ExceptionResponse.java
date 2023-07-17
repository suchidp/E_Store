package com.inventoryservice.exception;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String message;
    public ExceptionResponse(List<String> message) {
    }
}
