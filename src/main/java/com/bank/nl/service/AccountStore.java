package com.bank.nl.service;

import com.bank.nl.model.Account;

import java.util.List;

public interface AccountStore {
    void createAccount(String userName);
    List<Account> getAccountsForUser(String userName);
}
