package com.bank.nl.model;

// Better to store sensitive data like password as byte[] instead of string to mitigate java heap inspection
public record PasswordHashPair(byte[] password, String hash) {
}
