package com.leovegas.wallet.controller;

import java.util.List;
import javax.transaction.Transactional;

import com.leovegas.wallet.exception.InsufficientAccountBalanceException;
import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.model.Transaction;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Operation(summary = "Withdrawal and credit player account")
    @ApiResponse(responseCode = "404", description = "Player does not exist")
    @ApiResponse(responseCode = "400", description = "Wrong value. Probably player does not have enough balance to perform operation")
    public Transaction createTransaction(
        @Parameter(name = "transaction id", description = "It has to be unique", example = "1")
        @PathVariable Long transactionId,
        @Parameter(name = "player id", description = "Player id. Player has to exist", example = "2")
            Long playerId,
        @Parameter(name = "value", description = "Value of operation. Must be different than 0. " +
            "When lower than 0 then operation is debit otherwise is credit.", example = "3")
            Long value) {
        if (value == null || value == 0) {
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

        return transactionRepository.save(new Transaction(transactionId, playerId, value));
    }

    @GetMapping
    @Operation(summary = "Get all player transactions")
    @ApiResponse(responseCode = "404", description = "Player does not exist")
    @ApiResponse(responseCode = "400", description = "You didn't provided correct playerId")
    public List<Transaction> getAllPlayerTransactions(@Parameter(description = "Get transactions of this player") Long playerId) {
        if (playerRepository.findById(playerId).isPresent()) {
            return transactionRepository.findAllByPlayerId(playerId);
        } else {
            throw new PlayerNotFoundException("Player with given id does not exist");
        }
    }
}
