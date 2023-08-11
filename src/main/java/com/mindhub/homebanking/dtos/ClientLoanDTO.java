package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long Id;
    private String name;
    private Long loanId;
    private String amount;
    private Integer payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        Id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }
    public ClientLoanDTO(){}

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
