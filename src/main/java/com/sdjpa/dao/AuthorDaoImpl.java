package com.sdjpa.dao;

import java.util.List;
import java.util.Optional;

import com.sdjpa.repositories.AuthorRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sdjpa.domain.Author;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class AuthorDaoImpl implements AuthorDao {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		return authorRepository.searchAuthorByLastNameLike(lastName);
	}

	@Override
	public Author getById(Long id) {
		return authorRepository.getReferenceById(id);
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public List<Author> findAllAuthorsByName(String lastName, Pageable pageable) {
		return authorRepository.findAuthorByLastName(lastName, pageable).getContent();
	}


	@Override
	public Author saveNewAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Author updateAuthor(Author author) {
		Author foundAuthor= authorRepository.getReferenceById(author.getId());
		foundAuthor.setFirstName(author.getFirstName());
		foundAuthor.setLastName(author.getLastName());
		return authorRepository.save(foundAuthor);
	}

	@Override
	public void deleteAuthorById(Long id) {
		authorRepository.deleteById(id);
	}

	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

}
