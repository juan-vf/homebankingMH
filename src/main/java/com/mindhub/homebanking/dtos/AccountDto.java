package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDto {
    private Long id;
    private String number;
    private LocalDate date;
    private Double Balance;
    private Set<TransactionDto> transactions;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.date = account.getCreationDate();
        this.Balance = account.getBalance();
        this.transactions = account.getTransactions()
                .stream()
                .map(transaction -> new TransactionDto(transaction))
                .collect(Collectors.toSet());
    }
    public AccountDto() {}

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public Set<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDto> transactionDtoList) {
        this.transactions = transactionDtoList;
    }
}
