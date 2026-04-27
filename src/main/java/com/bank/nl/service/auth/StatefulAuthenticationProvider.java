package com.bank.nl.service.auth;

import com.bank.nl.exception.BusinessException;
import com.bank.nl.model.ResponseCode;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StatefulAuthenticationProvider implements AuthenticationProvider {

    private final CredentialsDataStore credentialsDataStore;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        byte[] password = Objects.requireNonNull(authentication.getCredentials()).toString().getBytes(StandardCharsets.UTF_8);
        if (credentialsDataStore.isValid(password, authentication.getName())) {
            return UsernamePasswordAuthenticationToken.authenticated(authentication.getName(),
                    Objects.requireNonNull(authentication.getCredentials()).toString(),
                    List.of());
        }

        throw new BusinessException(ResponseCode.WRONG_CREDENTIALS, "Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
