package com.island.visitorcenter.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.island.visitorcenter" })
@SpringBootApplication

public class ReservationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run( ReservationServerApplication.class, args);
	}

}

