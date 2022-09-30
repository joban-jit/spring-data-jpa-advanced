package com.sdjpa.bootstrap;

import com.sdjpa.domain.Author;
import com.sdjpa.domain.Book;
import com.sdjpa.repositories.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.sdjpa.repositories.BookRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@Component
@Profile({"local", "default"})
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	@Override
	public void run(String... args) throws Exception {
		authorRepository.deleteAll();
		Author author000 = new Author("Random", "House");
		Author author001 = new Author("Brian", "Oriely");
		authorRepository.saveAll(List.of(author000,author001));
		authorRepository.findAll().forEach(author -> {
			System.out.println("Author id: "+author.getId());
			System.out.println("Author name: "+author.getFirstName()+" "+author.getLastName());
		});

		bookRepository.deleteAll();
		Book book000 = new Book("Domain Driven Design", "123", "RandomHouse", author000.getId());
		bookRepository.save(book000);
		Book book001 = new Book("Spring in Action", "234343", "Oriely", author001.getId());
		bookRepository.save(book001);
		bookRepository.findAll().forEach(book->{
			System.out.println("Book id: "+book.getId());
			System.out.println("Book Title: "+book.getTitle());
			System.out.println("Author id: "+book.getAuthorId());
		});




	}

}
