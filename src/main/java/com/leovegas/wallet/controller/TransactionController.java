package com.leovegas.wallet.controller;

import javax.transaction.Transactional;

import com.leovegas.wallet.exception.InsufficientAccountBalanceException;
import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.model.Transaction;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final PlayerRepository playerRepository;

    public TransactionController(TransactionRepository transactionRepository, PlayerRepository playerRepository) {
        this.transactionRepository = transactionRepository;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/{transactionId}")
    @Transactional
    public Transaction createTransaction(@PathVariable Long transactionId,
                                         Long playerId,
                                         Long value) {
        if (value == 0) {
            throw new IllegalArgumentException("Value must be different than 0");
        }

        if (transactionRepository.findById(transactionId).isPresent()) {
            throw new IllegalArgumentException("Transaction with given id already exists");
        }

        var player = playerRepository
            .findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("Player with given id does not exist"));

        if (player.getBalance() + value < 0) {
            throw new InsufficientAccountBalanceException("Player has insufficient account balance");
        }

        player.setBalance(player.getBalance() + value);
        playerRepository.save(player);

        var transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setPlayerId(playerId);
        transaction.setValue(value);

        return transactionRepository.save(transaction);
    }
}
