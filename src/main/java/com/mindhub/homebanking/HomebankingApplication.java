package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository
	){
		return (args) -> {



			Client client = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			clientRepository.save(client);

			Account VIN001 = new Account("VIN001", LocalDate.now(), 5000.00);
			Account VIN002 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00);
			client.addAccount(VIN001);
			client.addAccount(VIN002);

			accountRepository.save(VIN001);
			accountRepository.save(VIN002);

			Transaction transaction001 = new Transaction(TransactionType.CREDITO, LocalDate.now(), "Various", "25000");
			Transaction transaction002 = new Transaction(TransactionType.DEBITO, LocalDate.now(), "Various", "-20000");

			VIN001.addTransactions(transaction001);
			VIN001.addTransactions(transaction002);

			transactionRepository.save(transaction001);
			transactionRepository.save(transaction002);

			Transaction transaction003 = new Transaction(TransactionType.CREDITO, LocalDate.now(), "Various", "38000");
			Transaction transaction004 = new Transaction(TransactionType.DEBITO, LocalDate.now(), "Various", "-30000");

			VIN002.addTransactions(transaction003);
			VIN002.addTransactions(transaction004);

			transactionRepository.save(transaction003);
			transactionRepository.save(transaction004);

			List<Integer> quotaList1 = new ArrayList<>();
			Collections.addAll(quotaList1 ,12,24,36,48,60);

			List<Integer> quotaList2 = new ArrayList<Integer>();
			Collections.addAll(quotaList2 ,6,12,24);

			List<Integer> quotaList3 = new ArrayList<Integer>();
			Collections.addAll(quotaList3 ,6,12,24,36);
			/*
			Loan loan1 = new Loan("Hipotecario", "500.000", quotaList1);
			Loan loan2 = new Loan("Personal", "100.000", quotaList2);
			Loan loan3 = new Loan("Automotriz", "300.000", quotaList3);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			*/
			Loan loan4 = new Loan("Hipotecario", "400.000", quotaList1);
			loanRepository.save(loan4);
			Loan loan2 = new Loan("Personal", "100.000", quotaList2);
			loanRepository.save(loan2);
			ClientLoan clientLoan1 = new ClientLoan("400.000", 60, client, loan4);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan("50.000", 12, client, loan2);
			clientLoanRepository.save(clientLoan2);

			Card card1 = new Card(
					"0000-0000-0000",
					357,
					LocalDate.now(),
					LocalDate.now().plusYears(5),
					client.getFirstName()+client.getLastName(),
					CardType.CREDIT,
					CardColor.GOLD
			);
			Card card2 = new Card(
					"0000-0000-0001",
					526,
					LocalDate.now(),
					LocalDate.now().plusYears(5),
					client.getFirstName()+client.getLastName(),
					CardType.CREDIT,
					CardColor.TITANIUM
			);
			Card card3 = new Card(
					"0000-0000-0002",
					847,
					LocalDate.now(),
					LocalDate.now().plusYears(5),
					client.getFirstName()+client.getLastName(),
					CardType.DEBIT,
					CardColor.SILVER
			);
			client.addCard(card1);
			client.addCard(card2);
			client.addCard(card3);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};
	}

}

