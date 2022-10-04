package com.sdjpa.dao;

import java.util.List;

import com.sdjpa.domain.Author;

public interface AuthorDao {
	List<Author> listAuthorByLastNameLike(String lastName);
	
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);
    Author findAuthorByNameUsingNamedQuery(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

	List<Author> findAll();

	Author findAuthorByNameUsingCriteria(String firstName, String lastName);

	Author findAuthorByNameUsingNativeQuery(String firstName, String lastName);
}