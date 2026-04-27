package com.bank.nl.api;

import com.bank.nl.model.AccessToken;
import com.bank.nl.model.LoginRequest;
import com.bank.nl.service.auth.SessionManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginDelegate implements LoginApiDelegate {

    private final AuthenticationManager authenticationManager;
    private final SessionManager sessionManager;

    @Override
    public ResponseEntity<AccessToken> loginCustomer(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UUID sessionToken = sessionManager.createNewSession();
        return ResponseEntity.ok().body(AccessToken.builder().token(sessionToken.toString()).build());
    }
}
