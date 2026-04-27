package com.bank.nl.security;

import com.bank.nl.filter.SessionValidateFilter;
import com.bank.nl.model.Error;
import com.bank.nl.model.ResponseCode;
import com.bank.nl.service.auth.SessionManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration
@AllArgsConstructor
public class AppSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain createSecurityFilter(HttpSecurity http,
                                                    AuthenticationProvider authenticationProvider) {

        return http
                .securityMatcher("/login", "/register")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain createBearerSecurityFilter(HttpSecurity http,
                                                          SessionManager sessionManager,
                                                          final ObjectMapper mapper) {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/overview")
                .addFilterAfter(new SessionValidateFilter(sessionManager), ExceptionTranslationFilter.class)
                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                            var errorResponse = Error.builder()
                                    .message(authException.getMessage())
                                    .code(ResponseCode.WRONG_CREDENTIALS.getCode())
                                    .build();
                            String body = mapper.writeValueAsString(errorResponse);
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write(body.formatted(authException.getMessage()));
                    })
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
