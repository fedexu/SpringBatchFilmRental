package com.filmrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FilmrentalApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(FilmrentalApplication.class, args);
	}
}

