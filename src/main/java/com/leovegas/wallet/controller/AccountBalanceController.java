package com.leovegas.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountBalanceController {

    @GetMapping("/wallet")
    public String getBalance(Long playerId) {
        return "My balance";
    }
}
