package com.FinApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinAppApplication {

	private static final Logger log = LoggerFactory.getLogger(FinAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FinAppApplication.class, args);
	}
}
