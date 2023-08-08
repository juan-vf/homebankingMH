package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
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
        Optional<Client> clientOptional = clientR.findById(id);
        return  new ClientDto(clientOptional.get());
    };
}
