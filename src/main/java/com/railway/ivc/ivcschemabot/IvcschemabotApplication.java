package com.railway.ivc.ivcschemabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for bot.
 * <p>
 * @author Viktor Zaitsev.
 */
@SpringBootApplication
@EnableScheduling
public class IvcschemabotApplication {

	/**
	 * Main method for bot application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(IvcschemabotApplication.class, args);
	}

}
