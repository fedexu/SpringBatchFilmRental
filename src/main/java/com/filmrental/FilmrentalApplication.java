package com.filmrental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.filmrental.data.entity.Actor;
import com.filmrental.data.repository.ActorRepository;

@SpringBootApplication
@EnableMongoRepositories
public class FilmrentalApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FilmrentalApplication.class, args);
	}

	@Autowired
	private ActorRepository repository;
	
	@Override
	public void run(String... args) throws Exception {

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Actor customer : repository.findAll()) {
			System.out.println(customer);
		}

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Actor actor : repository.findByLastName("Smith")) {
			System.out.println(actor);
		}
		
		System.out.println("--------------------------------");
	}

}

