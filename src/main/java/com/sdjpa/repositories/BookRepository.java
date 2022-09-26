package com.sdjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdjpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
