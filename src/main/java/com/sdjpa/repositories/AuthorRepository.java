package com.sdjpa.repositories;

import com.sdjpa.domain.Author;
import com.sdjpa.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);

    @Query("Select a from Author a where a.lastName like %:lastName%")
    List<Author> searchAuthorByLastNameLike(@Param("lastName") String lastName);
}
