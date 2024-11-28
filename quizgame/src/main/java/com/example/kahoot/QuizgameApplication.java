package com.example.kahoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.app"})
public class QuizgameApplication  {

	public static void main(String[] args) {
		SpringApplication.run(QuizgameApplication.class, args);
	}

}
