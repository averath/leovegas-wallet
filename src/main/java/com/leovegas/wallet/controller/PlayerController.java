package com.leovegas.wallet.controller;

import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping
    @Operation(summary = "Save player with 0 balance and new id.")
    public Player createPlayer() {
        return playerRepository.save(new Player());
    }

    @GetMapping("{playerId}")
    @Operation(summary = "Get player with given id")
    public Player getPlayer(@PathVariable Long playerId) {
        return playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("Player with given id does not exist."));
    }
}
