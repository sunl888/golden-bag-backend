package com.zmdev.goldenbag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoldenBagApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenBagApplication.class, args);
	}
}
