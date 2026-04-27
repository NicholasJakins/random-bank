package com.bank.nl.api;

import com.bank.nl.model.AccessToken;
import com.bank.nl.model.LoginRequest;
import com.bank.nl.service.auth.CredentialsDataStore;
import com.bank.nl.service.auth.SessionManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @MockitoBean
    CredentialsDataStore credentialsDataStore;

    @MockitoBean
    SessionManager sessionManager;

    @Test
    public void testRegisterCustomer_Success() {
        String name = "name";
        String password = "password";
        UUID token = UUID.randomUUID();

        Mockito.when(credentialsDataStore.isValid(password.getBytes(StandardCharsets.UTF_8), name))
                .thenReturn(true);

        Mockito.when(sessionManager.createNewSession())
                .thenReturn(token);

        var response = doLogin(createLoginRequest(password, name));

        assertEquals(response.getBody().getToken(), token.toString());
    }

    @Test
    public void testRegisterCustomer_BadCredentials() {
        String name = "name";
        String password = "password";

        Mockito.when(credentialsDataStore.isValid(password.getBytes(StandardCharsets.UTF_8), name))
                .thenReturn(false);

        Exception ex = assertThrows(
                Exception.class,
                () -> doLogin(createLoginRequest(password, name))
        );

        assertTrue(ex.getMessage().contains("Invalid username or password"));
    }

    private LoginRequest createLoginRequest(String pass, String name) {
        return LoginRequest.builder().password(pass).username(name).build();
    }

    private ResponseEntity<AccessToken> doLogin(LoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> requestEntity =
                new HttpEntity<>(request, headers);

        var url = "http://localhost:%d/login".formatted(port);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                AccessToken.class);
    }
}
