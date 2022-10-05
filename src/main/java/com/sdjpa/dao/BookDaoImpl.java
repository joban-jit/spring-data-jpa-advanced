package com.sdjpa.dao;

import java.util.List;

import com.sdjpa.repositories.BookRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sdjpa.domain.Book;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookDaoImpl implements BookDao {

	@Autowired
	private BookRepository bookRepository;

	

	@Override
	public Book getById(Long id) {
		return bookRepository.getReferenceById(id);
	}

	@Override
	public Book findBookByTitle(String title) {
		return bookRepository.findBookByTitle(title)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Book saveNewBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public Book updateBook(Book book) {
		Book foundBook = bookRepository.getReferenceById(book.getId());
		foundBook.setTitle(book.getTitle());
		foundBook.setIsbn(book.getIsbn());
		foundBook.setAuthorId(book.getAuthorId());
		foundBook.setPublisher(book.getPublisher());
		return bookRepository.save(foundBook);

	}

	@Override
	public void deleteBookById(Long id) {
		bookRepository.deleteById(id);
	}
	
	@Override
	public Book findByISBN(String isbn) {
		return bookRepository.findBookByIsbn(isbn)
				.orElseThrow(EntityNotFoundException::new);
	}
	
	@Override
	public List<Book> findAllBooks(int offset, int pageSize) {
		Pageable pageable = PageRequest.of(offset, pageSize);
		if (offset>0){
			pageable = pageable.withPage(offset/pageSize);
		}else{
			pageable = pageable.withPage(0);
		}
		return findAllBooks(pageable);
	}
	
	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable).getContent();
	}
	
	@Override
	public List<Book> findAllBooksSortByTitle(Pageable pageable) {
		Page<Book> bookPage = bookRepository.findAll(pageable);

		return bookPage.getContent();
	}


}
