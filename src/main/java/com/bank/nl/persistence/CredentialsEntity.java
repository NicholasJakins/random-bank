package com.bank.nl.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsEntity {
    @Id
    private String userName;

    @Column(name = "password")
    private String passwordHash;
}
