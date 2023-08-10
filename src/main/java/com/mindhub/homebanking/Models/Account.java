package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.enums.TransactionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double Balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    public Account(String number, LocalDate creationDate, Double balance) {
        this.number = number;
        this.creationDate = creationDate;
        Balance = balance;
    }
    public Account() {}

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }


    public void addTransactions(Transaction transaction) {
        transaction.setAccount(this);
        //setBalance(collectDebit(transactions.getAmount()));
        this.transactions.add(transaction);
        modifyAmount(transaction);
    }

    public void modifyAmount(Transaction transaction){
        if (transaction.getType() == TransactionType.DEBITO) {
            setBalance(getBalance() + Double.parseDouble(transaction.getAmount()));
        } else if(transaction.getType() == TransactionType.CREDITO) {
            setBalance(getBalance() + Double.parseDouble(transaction.getAmount()));
        }
    }
}
