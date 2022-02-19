package com.leovegas.wallet.repository;

import com.leovegas.wallet.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository  extends CrudRepository<Transaction, Long> {
}
