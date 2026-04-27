package com.bank.nl.api;

import com.bank.nl.model.Account;
import com.bank.nl.model.AccountResponse;
import com.bank.nl.model.LoginRequest;
import com.bank.nl.service.AccountStore;
import com.bank.nl.service.auth.SessionManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OverviewTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @MockitoBean
    SessionManager sessionManager;

    @MockitoBean
    AccountStore accountStore;

    @Test
    public void overViewSuccess() {
        UUID token = UUID.randomUUID();
        String userName = "userName";
        var account = new Account("iban", "EUR", new BigDecimal(0));
        Mockito.when(sessionManager.isValidSession(token)).thenReturn(true);
        Mockito.when(accountStore.getAccountsForUser(userName))
                .thenReturn(List.of(account));

        var response = getOverview(userName, token.toString());
        assertEquals(response.getBody().get(0).getIban(), "iban");
    }

    @Test
    public void overView_FailedAuth() {
        UUID token = UUID.randomUUID();
        String userName = "userName";
        Mockito.when(sessionManager.isValidSession(token)).thenReturn(false);

        Exception ex = assertThrows(
                Exception.class,
                () -> getOverview(userName, token.toString())
        );

        assertTrue(ex.getMessage().contains("Session token provided is invalid"));
    }

    private ResponseEntity<List<AccountResponse>> getOverview(String userName, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer %s".formatted(token));

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:%d/overview".formatted(port))
                .queryParam("username", userName)
                .build()
                .toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
