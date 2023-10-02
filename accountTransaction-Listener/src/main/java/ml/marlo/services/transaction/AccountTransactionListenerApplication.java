package ml.marlo.services.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class AccountTransactionListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountTransactionListenerApplication.class, args);
	}

}
