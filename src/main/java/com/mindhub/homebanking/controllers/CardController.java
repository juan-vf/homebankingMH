package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            Authentication authentication,
            @RequestParam CardColor cardColor,
            @RequestParam CardType cardType
            ){
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = client.getCards();
        if(cards.size() >= 3){
            return new ResponseEntity<>("Maximo de tarjetas alcanzado", HttpStatus.FORBIDDEN);
        }
        Random random = new Random();
        int randomNumber = random.nextInt(999 - 0 + 1) + 0;
        String clientName = client.getFirstName() + " " + client.getLastName();
        String cardNumber = generateUniqueRandomCardNumber(cardRepository.findAllNumber());

        Card card = new Card(cardNumber, randomNumber, LocalDate.now(), LocalDate.now().plusYears(5), clientName, cardType, cardColor);
        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>("Tarjeta creada", HttpStatus.OK);
    }
    public static String generateUniqueRandomCardNumber(List<String> existingCombinations) {
        Random random = new Random();
        StringBuilder creditCardNumber = new StringBuilder();
        boolean isUnique = false;
        while (!isUnique) {
            creditCardNumber.setLength(0);
            for (int i = 0; i < 16; i++) {
                int digit = random.nextInt(10);
                creditCardNumber.append(digit);

                if ((i + 1) % 4 == 0 && i != 15) {
                    creditCardNumber.append("-");
                }
            }
            String generatedNumber = creditCardNumber.toString();

            if (!existingCombinations.contains(generatedNumber)) {
                isUnique = true;
            }
        }
        return creditCardNumber.toString();
    }
}
