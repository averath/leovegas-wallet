package com.leovegas.wallet.controller;

import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.repository.PlayerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountBalanceController {
    private final PlayerRepository playerRepository;

    public AccountBalanceController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/players/{playerId}/balance")
    public Long getBalance(@PathVariable Long playerId) {
        return playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("Player with given id does not exist"))
            .getBalance();
    }
}
