package com.bank.nl.model;

import java.math.BigDecimal;

public record Account(String iban, String currency, BigDecimal amount) {
}
