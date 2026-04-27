package com.bank.nl.service;

import com.bank.nl.model.Customer;
import com.bank.nl.persistence.CredentialsEntity;
import com.bank.nl.persistence.CredentialsRepository;
import com.bank.nl.persistence.CustomerEntity;
import com.bank.nl.persistence.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDataStoreImpl implements CustomerDataStore {
    private final CustomerRepository customerRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordHandler passwordGenerator;

    public byte[] createCustomer(Customer customer) {

        var password = passwordGenerator.generatePassword(16);

        var customerEntity = CustomerEntity.builder()
                .addressLine1(customer.address().getLine1())
                .addressLine2(customer.address().getLine2())
                .addressLine3(customer.address().getLine3())
                .firstname(customer.firstName())
                .lastname(customer.lastName())
                .birthdate(customer.birthDate())
                .build();

        var credentialsEntity = CredentialsEntity.builder()
                .passwordHash(password.hash())
                .userName(customer.username())
                .build();

        customerRepository.save(customerEntity);
        credentialsRepository.save(credentialsEntity);

        return password.password();
    }
}
