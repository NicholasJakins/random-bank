package com.bank.nl.service;

import com.bank.nl.model.Customer;

public interface CustomerDataStore {
    byte[] createCustomer(Customer customer);
}
