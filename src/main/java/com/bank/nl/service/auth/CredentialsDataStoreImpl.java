package com.bank.nl.service.auth;

import com.bank.nl.persistence.CredentialsRepository;
import com.bank.nl.service.PasswordHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CredentialsDataStoreImpl implements CredentialsDataStore {

    private CredentialsRepository repository;
    private PasswordHandler passwordHandler;

    public boolean userNameExists(String userName) {
        return repository.findById(userName).isPresent();
    }

    public boolean isValid(byte[] password, String userName) {
        String passwordHash = passwordHandler.getPasswordHash(password);
        return repository.findByUserNameAndPasswordHash(userName, passwordHash).isPresent();
    }
}
