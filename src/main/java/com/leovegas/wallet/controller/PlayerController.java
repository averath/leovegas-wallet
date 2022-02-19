package com.leovegas.wallet.controller;

import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.repository.PlayerRepository;
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
    public Player createPlayer() {
        return playerRepository.save(new Player());
    }
}
