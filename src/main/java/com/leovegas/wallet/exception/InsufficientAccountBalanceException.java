package com.leovegas.wallet.exception;

public class InsufficientAccountBalanceException extends RuntimeException {
    public InsufficientAccountBalanceException(String message) {
        super(message);
    }
}
