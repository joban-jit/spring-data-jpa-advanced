package com.sdjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdjpa.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitle(String title);
    Optional<Book> findBookByIsbn(String isbn);

    Stream<Book> findAllByTitleNotNull();

    // using package-info.java file we are also letting all the repositories in that package can return null values
    // and also accept null parameters
    Book readByTitle(String title);

    // here below both result and parameter can be null
    @Nullable
    Book getByTitle(@Nullable String title);

    @Async
    Future<Book> queryByTitle(String title);

    @Async
    CompletableFuture<Book> queryByIsbn(String isbn);

    @Query("Select b From Book b Where b.title=?1")//, using positional params:HQL
    Book findBookByTitleWithQuery(String title);

    @Query("Select b From Book b Where b.title=:title")// using named param: HQL
    Book findBookByTitleWithQueryNamed(@Param("title") String title);


    @Query(value = "Select * from book where title=:title", nativeQuery = true)
    Book findBookByTitleNativeQuery(@Param("title") String title);

    Book findBookByTitleNamed(@Param("title") String title);




}
