package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;

public class TransactionDto {
    private Long id;
    private TransactionType type;
    private LocalDate date;
    private String description;
    private String amount;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
    }
    public TransactionDto() {}

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}
