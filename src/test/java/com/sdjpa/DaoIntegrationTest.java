package com.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.sdjpa.dao.AuthorDao;
import com.sdjpa.dao.AuthorDaoImpl;
import com.sdjpa.dao.BookDao;
import com.sdjpa.dao.BookDaoImpl;
import com.sdjpa.domain.Author;
import com.sdjpa.domain.Book;

import net.bytebuddy.utility.RandomString;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

@DataJpaTest
@Import({AuthorDaoImpl.class, BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DaoIntegrationTest {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Long id = saved.getId();

        assertThrows(JpaObjectRetrievalFailureException.class, ()->  bookDao.getById(id));
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);
        
//        book.setAuthor(author);

        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

//        book.setAuthor(author);
        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookBytitle() {
        Book book = bookDao.findBookByTitle("Spring in Action");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(5L);

        assertThat(book.getId()).isNotNull();
    }
    
    @Test
    void testFindByISBN() {
    	Book book = new Book();
    	book.setIsbn("1234"+RandomString.make());
    	book.setTitle("ISBN TEST");
    	Book savedBook = bookDao.saveNewBook(book);
    	Book foundBook = bookDao.findByISBN(book.getIsbn());
    	assertEquals("ISBN TEST", foundBook.getTitle());
    }

    
    @Test
    void testFindAllBooksUsingPageable() {
    	List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 5));
    	assertNotNull(books);
    	assertEquals(5, books.size());
    }
    
    @Test
    void testFindAllBooksUsingPageSizeAndPageOffset() {
    	List<Book> books = bookDao.findAllBooks(0, 5);
    	assertNotNull(books);
    	assertEquals(5, books.size());
    }
    @Test
    void testFindAllBooksUsingPageSizeAndPageOffsetPage2() {
    	List<Book> books = bookDao.findAllBooks(5, 5);
    	assertNotNull(books);
    	assertEquals(5, books.size());
    }
    
    @Test
    void testFindAllBooksSortByTitle() {
    	List<Book> sortedBooks = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title"))));
    	assertNotNull(sortedBooks);
    	assertEquals(10, sortedBooks.size());
    	sortedBooks.stream().forEachOrdered(book->{
    		System.out.println(book.getTitle());
    	});
    	
    	
    }


    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());
        long id = saved.getId();
        assertThrows(JpaObjectRetrievalFailureException.class,
                ()-> authorDao.getById(id)
        );


    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);
       
      
        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertNotEquals(0, saved.getId());
        System.out.println("saved id is "+saved.getId());
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Random", "House");
        assertThat(author).isNotNull();
    }


    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(5L);

        assertThat(author).isNotNull();
        assertEquals(5L, author.getId());

    }
    
    @Test
    void testFindAllAuthorsUsingNamedQuery() {
    	List<Author> authors = authorDao.findAll();
    	assertNotNull(authors);
    	assertTrue(authors.size()>0);
    }
    
    
    @Test
    void testGetAuthorWithLike() {
    	List<Author> resultList = authorDao.listAuthorByLastNameLike("Oriel");
    	resultList.forEach(author->{
    		assertEquals("Oriely", author.getLastName());
    	});
    }

    @Test
    void testFindAuthorByLastNameWithPage(){
        List<Author> authors = authorDao.findAllAuthorsByName("Oriely", PageRequest.of(0, 5));
        authors.forEach(author->{
            assertEquals("Oriely", author.getLastName());
        });
        assertEquals(2, authors.size());
    }

    @Test
    void testFindAuthorByLastNameWithPageAndSort(){
        List<Author> authors = authorDao.findAllAuthorsByName("Oriely", PageRequest.of(
                0,
                5,
                Sort.by(Sort.Order.desc("firstName"))
        ));
        authors.forEach(author->{
            assertEquals("Oriely", author.getLastName());
        });
        assertEquals("John", authors.get(0).getFirstName());

        assertEquals(2, authors.size());
    }

    @Test
    void testFindAuthorByLastNameWithPageAndSortAsc(){
        List<Author> authors = authorDao.findAllAuthorsByName("Oriely", PageRequest.of(
                0,
                5,
                Sort.by(Sort.Order.asc("firstName"))
        ));
        authors.forEach(author->{
            assertEquals("Oriely", author.getLastName());
        });
        assertEquals("Brian", authors.get(0).getFirstName());

        assertEquals(2, authors.size());
    }



    

}