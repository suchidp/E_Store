package com.inventoryservice.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationRequest {
    private String token;
}
