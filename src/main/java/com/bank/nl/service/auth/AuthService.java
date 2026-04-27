package com.bank.nl.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthService {
    Optional<Authentication> authenticate(HttpServletRequest request);
}
