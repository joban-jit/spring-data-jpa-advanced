package com.sdjpa;


import com.sdjpa.domain.Book;
import com.sdjpa.repositories.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
@ComponentScan(basePackages = "com.sdjpa.dao")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private  BookRepository bookRepository;

    @Test
    void testEmptyResultException(){
        assertThrows(EmptyResultDataAccessException.class,
                ()->bookRepository.readByTitle("foobar4"));
    };

    @Test
    void testNullParam(){
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoException(){
        assertNull(bookRepository.getByTitle("foo"));
    }

    @Test
    void testBookStream(){
        AtomicInteger count = new AtomicInteger();
        bookRepository.findAllByTitleNotNull().forEach(book->{
            count.incrementAndGet();
        });
        assertTrue(count.get()>5);
    }

    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = bookRepository.queryByTitle("Spring in Action");
        Book book = bookFuture.get();
        assertEquals("Spring in Action", book.getTitle());
    }

    @Test
    void testBookCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<Book> bookCf = bookRepository.queryByIsbn("123");
        String title = bookCf.thenApply(Book::getTitle).get();
        assertEquals("Domain Driven Design", title);

    }

    @Test
    void testFindBookByTitleWithQuery(){
        Book book = bookRepository.findBookByTitleWithQuery("Spring in Action");
        assertEquals("Spring in Action", book.getTitle());
    }

    @Test
    void testFindBookByTitleWithQueryNamed(){
        Book book = bookRepository.findBookByTitleWithQueryNamed("Spring in Action");
        assertEquals("Spring in Action", book.getTitle());
    }

    @Test
    void testFindBookByTitleNativeQuery(){
        Book book = bookRepository.findBookByTitleNativeQuery("Spring in Action");
        assertEquals("Spring in Action", book.getTitle());
    }

    @Test
    void testFindBookByTitleNamedQuery(){
        Book book = bookRepository.findBookByTitleNamed("Spring in Action");
        assertEquals("Spring in Action", book.getTitle());
    }

}
