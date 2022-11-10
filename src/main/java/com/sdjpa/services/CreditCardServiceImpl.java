package com.sdjpa.services;

import com.sdjpa.domain.creditcard.CreditCard;
import com.sdjpa.domain.creditcardholder.CreditCardHolder;
import com.sdjpa.domain.pan.CreditCardPAN;
import com.sdjpa.repositories.creditcard.CreditCardRepository;
import com.sdjpa.repositories.creditcardholder.CreditCardHolderRepository;
import com.sdjpa.repositories.pan.CreditCardPANRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService{

    private final CreditCardRepository creditCardRepository;
    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardPANRepository creditCardPANRepository;


    @Transactional
    @Override
    public CreditCard getCreditCardById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        CreditCardHolder creditCardHolder = creditCardHolderRepository.findByCreditCardId(id).orElseThrow();
        CreditCardPAN creditCardPan = creditCardPANRepository.findByCreditCardId(id).orElseThrow();

        creditCard.setFirstName(creditCardHolder.getFirstName());
        creditCard.setLastName(creditCardHolder.getLastName());
        creditCard.setCreditCardNumber(creditCardPan.getCreditCardNumber());
        creditCard.setZipCode(creditCardHolder.getZipCode());

        return creditCard;

    }


    @Transactional
    @Override
    public CreditCard saveCreditCard(CreditCard cc) {
        CreditCard savedCC = creditCardRepository.save(cc);
        savedCC.setFirstName(cc.getFirstName());
        savedCC.setLastName(cc.getLastName());
        savedCC.setCreditCardNumber(cc.getCreditCardNumber());
        savedCC.setZipCode(cc.getZipCode());

        CreditCardHolder creditCardHolder = CreditCardHolder.builder()
                .creditCardId(savedCC.getId())
                .firstName(savedCC.getFirstName())
                .lastName(savedCC.getLastName())
                .zipCode(savedCC.getZipCode())
                .build();
        creditCardHolderRepository.save(creditCardHolder);

        CreditCardPAN creditCardPan = CreditCardPAN.builder()
                .creditCardId(savedCC.getId())
                .creditCardNumber(savedCC.getCreditCardNumber())
                .build();
        creditCardPANRepository.save(creditCardPan);


        return savedCC;
    }

    @Transactional("cardTransactionManager")
    @Override
    public CreditCard getCreditCard(Long id) {
        return creditCardRepository.findById(id).orElseThrow();

    }
}
