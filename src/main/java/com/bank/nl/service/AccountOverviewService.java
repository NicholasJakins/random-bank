package com.bank.nl.service;

import com.bank.nl.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountOverviewService {

    private final AccountStore accountStore;

    public List<Account> getAccountsForUser(String userName) {
        return accountStore.getAccountsForUser(userName);
    }
}
