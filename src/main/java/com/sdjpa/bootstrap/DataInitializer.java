package com.sdjpa.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sdjpa.repositories.BookRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}
