package com.sdjpa.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sdjpa.domain.Book;

public interface BookDao {
  
	Book findByISBN(String isbn);
	List<Book> findAll();
	Book findBookByTitleUsingNamedQuery(String title);
	Book getById(Long id);
	Book findBookByTitle(String title);
	Book saveNewBook(Book book);
	Book updateBook(Book book);
	void deleteBookById(Long id);
	Book findBookByTitleUsingNativeQuery(String title);
	List<Book> findAllBooksSortByTitle(Pageable pageable);
	List<Book> findAllBooks(Pageable pageable);
	List<Book> findAllBooks(int offset, int PageSize);
}