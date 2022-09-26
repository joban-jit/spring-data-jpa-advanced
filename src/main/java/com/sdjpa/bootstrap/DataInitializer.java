package com.sdjpa.bootstrap;

import com.sdjpa.domain.Book;
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
		Book book000 = new Book("Domain Driven Design", "123", "RandomHouse");
		Book saved000 = bookRepository.save(book000);
		System.out.println("Id: "+book000.getId());
		Book book001 = new Book("Spring in Action", "234343", "Oriely");
		Book saved001 = bookRepository.save(book001);
		System.out.println("Id: "+book001.getId());
		bookRepository.findAll().forEach(book->{
			System.out.println("Book id: "+book.getId());
			System.out.println("Book Title: "+book.getTitle());
		});

	}

}
