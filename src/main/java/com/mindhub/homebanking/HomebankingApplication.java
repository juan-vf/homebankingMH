package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
			Client client = new Client("Melba", "Morel", "melba@mindhub.com", "00.000.000");
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





		};
	}

}
