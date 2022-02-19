package com.leovegas.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountBalanceController {

    @GetMapping
    public String getBalance() {
        return "My balance";
    }
}
