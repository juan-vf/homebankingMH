package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @GetMapping("/accounts")
    public List<AccountDto> getAccounts(){
        List<AccountDto> accountDtoList = accountRepository.findAll().stream().map(AccountDto::new).collect(Collectors.toList());
        return accountDtoList;
    }
    @GetMapping("/accounts/{id}")
    public AccountDto getAccountById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);
        AccountDto accountDto = new AccountDto(account);
        return accountDto;
    }
}
