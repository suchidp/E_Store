
package com.inventoryservice.jwtutil;
import com.inventoryservice.config.UserInfoUserDetails;
import com.inventoryservice.exception.InvalidTokenException;
import com.inventoryservice.model.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Order(1)
@Component
public class TokenValidator extends OncePerRequestFilter {

    private final AuthClient authClient;

    @Autowired
    public TokenValidator(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, InvalidTokenException {
        String token = request.getHeader("Authorization");
        TokenValidationRequest validationRequest = new TokenValidationRequest();
        validationRequest.setToken(token);
        TokenValidationResponse responses = authClient.validateToken(validationRequest);
        String role = responses.getRole();
        boolean isTokenValid = responses.isValidToken();
        if (isTokenValid) {
            UserInfoUserDetails userDetails = new UserInfoUserDetails(role);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            filterChain.doFilter(request, response);
        } else {
            throw new InvalidTokenException("token is invalid ");
        }
    }
}
