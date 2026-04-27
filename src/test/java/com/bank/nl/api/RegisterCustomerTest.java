package com.bank.nl.api;

import com.bank.nl.model.Address;
import com.bank.nl.model.RegisterCustomerRequest;
import com.bank.nl.model.RegisterCustomerResponse;
import com.bank.nl.service.AccountStore;
import com.bank.nl.service.CustomerDataStore;
import com.bank.nl.service.auth.CredentialsDataStore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterCustomerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @MockitoBean
    private CustomerDataStore customerDataStore;

    @MockitoBean
    private AccountStore accountStore;

    @MockitoBean
    private CredentialsDataStore credentialsDataStore;

    @Test
    public void testRegisterCustomer_Success() {
        var passwordForTest = "passphrase";
        Mockito.when(customerDataStore.createCustomer(any()))
                .thenReturn(passwordForTest.getBytes(StandardCharsets.UTF_8));

        var response = doPost(validCustomerRequest());

        assertEquals(response.getBody().getPassword(), passwordForTest);
    }

    @Test
    public void testRegisterCustomer_TooYoung() {
        var request = validCustomerRequest();
        request.setBirthdate(LocalDate.now().minusYears(2));

        Exception ex = assertThrows(
                Exception.class,
                () -> doPost(request)
        );

        assertTrue(ex.getMessage().contains("Customer must be 18 and above"));
    }

    @Test
    public void testRegisterCustomer_ExistingUserName() {
        var request = validCustomerRequest();

        Mockito.when(credentialsDataStore.userNameExists(any()))
                .thenReturn(true);

        Exception ex = assertThrows(
                Exception.class,
                () -> doPost(request)
        );

        assertTrue(ex.getMessage().contains("User already exists"));
    }

    public static RegisterCustomerRequest validCustomerRequest() {

        Address address = Address.builder()
                .line1("Keizersgracht 10")
                .line2("103 NA")
                .line3("Amsterdam")
                .countryCode(Address.CountryCodeEnum.BE)
                .build();

        return new RegisterCustomerRequest()
                .username("jane.doe")
                .firstName("Jane")
                .surname("Doe")
                .birthdate(LocalDate.now().minusYears(20))
                .address(address);
    }

    private ResponseEntity<RegisterCustomerResponse> doPost(RegisterCustomerRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegisterCustomerRequest> requestEntity =
                new HttpEntity<>(request, headers);

        var url = "http://localhost:%d/register".formatted(port);

        return restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        RegisterCustomerResponse.class);
    }

}