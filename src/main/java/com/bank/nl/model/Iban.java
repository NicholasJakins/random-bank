package com.bank.nl.model;

import java.security.SecureRandom;

public record Iban(String landCode, String bankCode) {
    private static final SecureRandom random = new SecureRandom();

    public String generateIban() {
        return landCode + generateRandomDigits(2) +
                bankCode +
                generateRandomDigits(10);
    }

    // Idea for future scope. Instead of random, perhaps a more accurate solution
    // is to keep some atomic counter of values.
    private String generateRandomDigits(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

}
