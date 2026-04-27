package com.bank.nl.service;

import com.bank.nl.model.PasswordHashPair;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordHandler {

    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private final SecureRandom random = new SecureRandom();

    public PasswordHashPair generatePassword(int length) {
        // can be improved by ensuring at least x number of special characters
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALLOWED_CHARS.length());
            password.append(ALLOWED_CHARS.charAt(index));
        }
        byte[] passwordBytes = password.toString().getBytes(StandardCharsets.UTF_8);
        return transformPassword(passwordBytes);
    }


    public String getPasswordHash(byte[] passwordBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = digest.digest(passwordBytes);
            return Base64.getEncoder().encodeToString(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private PasswordHashPair transformPassword(byte[] passwordBytes) {
        return new PasswordHashPair(passwordBytes, getPasswordHash(passwordBytes));
    }

}
