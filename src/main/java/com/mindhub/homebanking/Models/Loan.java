package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private String maxAmount;
    @ElementCollection
    private List<Integer> payments;

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clients;

    public Loan(String name, String maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }
    public Loan(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClients() {
        return clients;
    }

    public void setClients(Set<ClientLoan> clients) {
        this.clients = clients;
    }
}
