package com.bank.nl.model;

import com.bank.nl.exception.BusinessException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Customer(Address address, String username, String firstName, String lastName, LocalDate birthDate) {

    public void validate() {
        if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) < 18) {
            throw new BusinessException(ResponseCode.INVALID_CUSTOMER_AGE, "Customer must be 18 and above");
        }
    }
}
