package com.bank.nl.service;

import com.bank.nl.model.Account;
import com.bank.nl.model.Iban;
import com.bank.nl.persistence.AccountEntity;
import com.bank.nl.persistence.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountStoreImpl implements AccountStore {
    private final AccountRepository accountRepository;

    @Override
    public void createAccount(String userName) {
        var iban = new Iban("NL", "BANK");
        var accountEntity = AccountEntity.builder()
                .userName(userName)
                .iban(iban.generateIban())
                .balance(new BigDecimal(0))
                .currency("EUR")
                .build();

        accountRepository.save(accountEntity);
    }

    @Override
    public List<Account> getAccountsForUser(String userName) {
        return accountRepository.findByUserName(userName).stream()
                .map(entity -> new Account(entity.getIban(), entity.getCurrency(), entity.getBalance()))
                .toList();
    }
}
