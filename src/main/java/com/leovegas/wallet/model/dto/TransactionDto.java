package com.leovegas.wallet.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransactionDto {
    @NotNull(message = "Transaction id cannot be null")
    Long transactionId;
    @NotNull(message = "Player id cannot be null")
    Long playerId;
    @NotNull(message = "Value cannot be null")
    Long value;
}
