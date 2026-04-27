package com.bank.nl.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<CredentialsEntity, String> {
    Optional<CredentialsEntity> findByUserNameAndPasswordHash(String userName, String passwordHash);
}
