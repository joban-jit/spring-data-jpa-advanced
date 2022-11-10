package com.sdjpa.services;

import com.sdjpa.domain.creditcard.CreditCard;

public interface CreditCardService {
    CreditCard getCreditCardById(Long id);

    CreditCard saveCreditCard(CreditCard cc);

    CreditCard getCreditCard(Long id);
}
