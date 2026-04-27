package com.bank.nl.service.auth;


public interface CredentialsDataStore {
    boolean userNameExists(String userName);
    boolean isValid(byte[] password, String userName);
}
