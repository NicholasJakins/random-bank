package com.bank.nl.api;

import com.bank.nl.model.Address;
import com.bank.nl.model.Customer;
import com.bank.nl.model.RegisterCustomerRequest;
import com.bank.nl.model.RegisterCustomerResponse;
import com.bank.nl.service.RegisterCustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@AllArgsConstructor
public class RegisterDelegate implements RegisterApiDelegate {

    private final RegisterCustomerService registerCustomerService;

    @Override
    public ResponseEntity<RegisterCustomerResponse> registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        var address = Address.builder()
                .line1(registerCustomerRequest.getAddress().getLine1())
                .line2(registerCustomerRequest.getAddress().getLine2())
                .line3(registerCustomerRequest.getAddress().getLine3())
                .build();

        byte[] password = registerCustomerService.createCustomer(
                new Customer(address,
                        registerCustomerRequest.getUsername(),
                        registerCustomerRequest.getFirstName(),
                        registerCustomerRequest.getSurname(),
                        registerCustomerRequest.getBirthdate()));

        return ResponseEntity.ok().body(
                RegisterCustomerResponse.builder()
                        .password(new String(password, StandardCharsets.UTF_8))
                        .build());
    }
}
