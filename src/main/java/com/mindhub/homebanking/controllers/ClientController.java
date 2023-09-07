package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientR;
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/clients")
    public List<ClientDto> getClients(){
        List<ClientDto> clientList =  clientR.findAll().stream().map(ClientDto::new).collect(Collectors.toList());
        return clientList;
    }
    @GetMapping("/clients/{id}")
    public ClientDto getClientById(@PathVariable Long id){
        Client client = clientR.findById(id).orElse(null);
        return  new ClientDto(client);
    };

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password
    ){
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(clientR.findByEmail(email) != null){
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        //Create Client
        Client client = new Client(firstName, lastName, email,passwordEncoder.encode(password));
        clientR.save(client);
        //Generate account number/name
        List<String> accountNumberExistList = accountRepository.findAllNumbers();
        String accountNumber = getRandomNumberUsingNextInt(0000, 99999999, accountNumberExistList);
        //Generate account
        Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0 );
        //Add account to client and save account in database
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        return new ResponseEntity<>("Client registered", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ResponseEntity<ClientDto> currentClient(Authentication authentication){
        Client client = clientR.findByEmail(authentication.getName());
        ClientDto clientDto = new ClientDto(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication){
        Client client = clientR.findByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();
        if(accounts.size() >= 3){
            return new ResponseEntity<>("Ya tiene 3 cuentas", HttpStatus.FORBIDDEN);
        }

        List<String> accountNumberExistList = accountRepository.findAllNumbers();
        String accountNumber = getRandomNumberUsingNextInt(0000, 99999999, accountNumberExistList);
        Account newAccount = new Account( accountNumber, LocalDate.now(), 0.0);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        return new ResponseEntity<>("Cuenta creada", HttpStatus.OK);
    }
    public String getRandomNumberUsingNextInt(int min, int max, List<String> existList) {
        Random random = new Random();
        int randomNumber;
        String numberToCompare;
        String numberToReturn;
        do {
            randomNumber = random.nextInt(max - min + 1) + min;
            numberToCompare = "VIN-" + randomNumber;
        } while (existList.contains(numberToCompare));
        numberToReturn = "VIN-" + random.nextInt(max - min) + min;
        return numberToReturn;
    }
}
