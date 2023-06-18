package com.bence.mate.entity;

import com.bence.mate.annotation.PrimaryKey;
import com.bence.mate.annotation.Column;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    @PrimaryKey
    @Getter
    @Setter
    private long transactionId;

    @Column
    @Getter
    @Setter
    private int accountNumber;

    @Column
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String transactionType;

    @Column
    @Getter
    @Setter
    private int amount;
}
