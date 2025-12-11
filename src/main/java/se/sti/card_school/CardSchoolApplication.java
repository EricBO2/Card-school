package se.sti.card_school;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class CardSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardSchoolApplication.class, args);
	}

}
