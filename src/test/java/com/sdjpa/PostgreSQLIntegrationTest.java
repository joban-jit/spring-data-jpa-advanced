package com.sdjpa;

import com.sdjpa.repositories.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"com.sdjpa.bootstrap"})
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostgreSQLIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testPostgreSQl(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
