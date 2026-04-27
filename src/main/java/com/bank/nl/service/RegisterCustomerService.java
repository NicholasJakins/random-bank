package com.bank.nl.service;

import com.bank.nl.exception.BusinessException;
import com.bank.nl.model.Customer;
import com.bank.nl.model.ResponseCode;
import com.bank.nl.service.auth.CredentialsDataStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterCustomerService {

    private final CustomerDataStore customerDataStore;
    private final CredentialsDataStore credentialsDataStore;

    public byte[] createCustomer(Customer customer) {
        if (credentialsDataStore.userNameExists(customer.username())) {
            throw new BusinessException(ResponseCode.USER_ALREADY_EXISTS, "User already exists for customer %s".formatted(customer.username()));
        }

        return customerDataStore.createCustomer(customer);
    }
}
