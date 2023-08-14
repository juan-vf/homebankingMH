package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;

import java.time.LocalDate;

public class CardDto {
    private Long id;
    private String number;
    private Integer cvv;
    private LocalDate validityDates;
    private LocalDate thruDate;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    //private ClientDto clientDto;

    public CardDto(Card card) {
        this.id = card.getId();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.validityDates = card.getFromDate();
        this.thruDate = card.getDueDate();
        this.cardHolder = card.getCardholder();
        this.type = card.getType();
        this.color = card.getColor();
    }
    public CardDto(){}

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getValidityDates() {
        return validityDates;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }
}
