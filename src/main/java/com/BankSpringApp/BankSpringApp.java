package com.BankSpringApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class BankSpringApp {

	public static void main(String[] args) {
		SpringApplication.run(BankSpringApp.class, args);
	}

}