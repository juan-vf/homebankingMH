package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientR;
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
        Client client = new Client(firstName, lastName, email,passwordEncoder.encode(password));
        clientR.save(client);
        return new ResponseEntity<>("Client registered", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ResponseEntity<ClientDto> currentClient(Authentication authentication){
        Client client = clientR.findByEmail(authentication.getName());
        ClientDto clientDto = new ClientDto(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
}
