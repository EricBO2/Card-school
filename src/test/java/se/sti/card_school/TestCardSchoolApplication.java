package se.sti.card_school;

import org.springframework.boot.SpringApplication;

public class TestCardSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.from(CardSchoolApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
