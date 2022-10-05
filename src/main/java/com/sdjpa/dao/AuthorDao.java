package com.sdjpa.dao;

import java.util.List;

import com.sdjpa.domain.Author;
import org.springframework.data.domain.Pageable;

public interface AuthorDao {
	List<Author> listAuthorByLastNameLike(String lastName);
	
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> findAllAuthorsByName(String lastName, Pageable pageable);

	List<Author> findAll();
}