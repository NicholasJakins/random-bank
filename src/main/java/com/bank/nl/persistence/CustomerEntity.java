package com.bank.nl.persistence;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstname;
    private String lastname;
    private LocalDate birthdate;

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;

    // TODO: Country codes should be configured from application properties
    private String countryCode;

}
