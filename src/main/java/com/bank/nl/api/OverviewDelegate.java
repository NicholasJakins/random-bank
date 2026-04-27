package com.bank.nl.api;

import com.bank.nl.model.AccessToken;
import com.bank.nl.model.AccountResponse;
import com.bank.nl.service.AccountOverviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OverviewDelegate implements OverviewApiDelegate {

    private final AccountOverviewService overviewService;

    @Override
    public ResponseEntity<List<AccountResponse>> overviewGet(String username) {

        List<AccountResponse> response = overviewService.getAccountsForUser(username)
                .stream()
                .map(acc -> AccountResponse
                        .builder()
                        .iban(acc.iban())
                        .balance(acc.amount())
                        .currency(AccountResponse.CurrencyEnum.fromValue(acc.currency()))
                        .build())
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(response);
    }
}
