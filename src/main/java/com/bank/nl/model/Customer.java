package com.bank.nl.model;

import java.time.LocalDate;

public record Customer(Address address, String username, String firstName, String lastName, LocalDate birthDate) {
}
