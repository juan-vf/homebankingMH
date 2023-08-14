package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate dueDate;
    private String cardholder;

    private CardType type;
    private CardColor color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card(String number, Integer securityCode, LocalDate validityDates, LocalDate dueDate, String cardholderName, CardType cardType, CardColor cardColor) {
        this.number = number;
        this.cvv = securityCode;
        this.fromDate = validityDates;
        this.dueDate = dueDate;
        this.cardholder = cardholderName;
        this.type = cardType;
        this.color = cardColor;
    }
    public Card(){}

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
