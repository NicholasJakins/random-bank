package com.bank.nl.service;

import com.bank.nl.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersistentAuthService implements AuthService {

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        // Check password and username from cache
        return Optional.empty();
    }
}
