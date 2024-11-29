package com.example.kahoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.kahoot.model")
@EnableJpaRepositories(basePackages = "com.example.kahoot.repository")
public class  QuizgameApplication  {

	public static void main(String[] args) {
		SpringApplication.run(QuizgameApplication.class, args);
	}

}
