package com.leovegas.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Long playerId;
    private Long value;
}
