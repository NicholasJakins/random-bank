package com.bank.nl.filter;

import com.bank.nl.exception.BusinessException;
import com.bank.nl.model.ResponseCode;
import com.bank.nl.service.auth.SessionManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
public class SessionValidateFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Missing bearer token");
        }

        String sessionToken = authHeader.split(" ")[1];
        if (!sessionManager.isValidSession(UUID.fromString(sessionToken))) {
            throw new BadCredentialsException("Session token provided is invalid");
        }

        filterChain.doFilter(request, response);
    }
}
