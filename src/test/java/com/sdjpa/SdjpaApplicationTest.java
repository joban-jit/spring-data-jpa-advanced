package com.sdjpa;

import com.sdjpa.domain.creditcard.CreditCard;
import com.sdjpa.services.CreditCardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SdjpaApplicationTest {

    @Autowired
    private CreditCardService creditCardService;

    @Test
    void testSaveAndGetCreditCard(){
        CreditCard cc = CreditCard.builder()
                .firstName("John")
                .lastName("Wick")
                .zipCode("12345")
                .creditCardNumber("123456789")
                .cvv("123")
                .expirationDate("12/26")
                .build();
        CreditCard savedCc = creditCardService.saveCreditCard(cc);

        assertNotNull(savedCc);
        assertNotNull(savedCc.getId());
        assertNotNull(savedCc.getCreditCardNumber());

        CreditCard fetchedCc = creditCardService.getCreditCardById(savedCc.getId());

        assertNotNull(fetchedCc);
        assertNotNull(fetchedCc.getId());
        assertNotNull(fetchedCc.getCreditCardNumber());
        assertEquals("123456789" , fetchedCc.getCreditCardNumber());
    }
}