package org.accountclient.controller;

import org.accountclient.client.AccountClient;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Account client controller
 */
@RestController
public class AccountClientController {

    AccountClient accountClient;

    AccountClientController(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @GetMapping("/api/amount")
    public Long getAmount(@RequestParam("id") @NonNull Integer id) {
        return accountClient.getAmount(id);
    }

    @PostMapping("/api/amount")
    public void addAmount(@RequestParam("id") @NonNull Integer id, @RequestParam("value") @NonNull Long value) {
        accountClient.addAmount(id, value);
    }

}
