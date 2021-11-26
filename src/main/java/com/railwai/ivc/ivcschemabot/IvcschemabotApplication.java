package com.railwai.ivc.ivcschemabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IvcschemabotApplication {

	public static void main(String[] args) {
		SpringApplication.run(IvcschemabotApplication.class, args);
	}

}
