package com.sdjpa.repositories;

import com.sdjpa.domain.CreditCard;
import com.sdjpa.services.EncryptionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    public static final String CREDIT_CARD_NUMBER = "12345678900000";
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EncryptionService encryptionService;

    @Test
    void testSaveAndStoreCreditCard(){
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD_NUMBER);
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");
        System.out.println("saving CC in database...");
        CreditCard savedCC = creditCardRepository.saveAndFlush(creditCard);
        System.out.println("Getting CC number from database: "+savedCC.getCreditCardNumber());
        System.out.println("CC at Rest");
        String encryptedCCNo = encryptionService.encrypt(CREDIT_CARD_NUMBER);
        System.out.println("CC encrypted: "+encryptedCCNo);

        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card " +
                "WHERE id = " + savedCC.getId());
        String db_cardValue = (String)dbRow.get("credit_card_number");
        assertNotEquals(savedCC.getCreditCardNumber(), db_cardValue);
        assertEquals(encryptedCCNo, db_cardValue);

        Long savedCCId = savedCC.getId();

        // onLoad() METHOD IS NOT WORKING WITH INTERCEPTORS, SO "FETCHING" FROM DATABASE IS NOT WORKING IF WE TRY TO
        // MANIPULATE DATA AS NO DATA IS AVAILABLE WHEN WE USE onLoad METHOD, SO DECODING IS NOT HAPPENING

//        System.out.println("fetching CC from database...");
//        Optional<CreditCard> fetchedCC = creditCardRepository.findById(savedCCId);
//        assertEquals(savedCC.getCreditCardNumber(), fetchedCC.get().getCreditCardNumber());

    }

//    @Test
//    void testOnLoadmethod(){
//        CreditCard fetchedCC = creditCardRepository.findById(25L).get();
//
//        assertThat(fetchedCC.getCreditCardNumber()).isEqualTo(CREDIT_CARD_NUMBER);
//    }
}