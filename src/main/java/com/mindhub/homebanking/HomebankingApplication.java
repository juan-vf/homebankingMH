package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
	public CommandLineRunner initData(ClientRepository ClientR, AccountRepository accountR){
		return (args) -> {
			Client client = new Client("Melba", "Morel", "melba@mindhub.com", "00.000.000");
			ClientR.save(client);

			Account VIN001 = new Account("VIN001", LocalDate.now(), 5000.00);
			Account VIN002 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00);

			client.addAccount(VIN001);
			accountR.save(VIN001);
			client.addAccount(VIN002);
			accountR.save(VIN002);


		};
	}

}
