package com.leovegas.wallet.controller;

import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get balance of player with given id")
    public Long getBalance(@PathVariable Long playerId) {
        return playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("Player with given id does not exist"))
            .getBalance();
    }
}
