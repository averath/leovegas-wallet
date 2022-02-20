package com.leovegas.wallet.controller;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.leovegas.wallet.exception.InsufficientAccountBalanceException;
import com.leovegas.wallet.exception.PlayerNotFoundException;
import com.leovegas.wallet.model.Transaction;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    @Transactional
    @Operation(summary = "Withdrawal and credit player account")
    public Transaction createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        if (transactionDto.getValue() == 0) {
            throw new IllegalArgumentException("Value must be different than 0");
        }

        if (transactionRepository.findById(transactionDto.getTransactionId()).isPresent()) {
            throw new IllegalArgumentException("Transaction with given id already exists");
        }

        var player = playerRepository
            .findById(transactionDto.getPlayerId())
            .orElseThrow(() -> new PlayerNotFoundException("Player with given id does not exist"));

        if (player.getBalance() + transactionDto.getValue() < 0) {
            throw new InsufficientAccountBalanceException("Player has insufficient account balance");
        }

        player.setBalance(player.getBalance() + transactionDto.getValue());
        playerRepository.save(player);

        var transaction = Transaction.builder().id(transactionDto.getTransactionId())
            .playerId(transactionDto.getPlayerId())
            .value(transactionDto.getValue()).build();

        return transactionRepository.save(transaction);
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
