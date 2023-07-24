package com.inventoryservice.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {
    private boolean isValidToken;
    private String role;
}
