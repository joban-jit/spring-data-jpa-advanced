package com.sdjpa;

import com.sdjpa.domain.Book;
import com.sdjpa.repositories.BookRepository;
import org.antlr.v4.runtime.misc.Utils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"com.sdjpa.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class SpringBookJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

    @Order(1)
//    @Rollback(value=false)
    @Commit// it does same thing as Rollback(value=false)
    @Test
    void testJpaTestSlice(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
        bookRepository.save(new Book("My Book", "123", "self", null));
        long countAfter = bookRepository.count();
        assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2)
    @Test
    void testJpaTestSliceTransactions(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);
    }
}
