package com.sdjpa.repositories.creditcard;

import com.sdjpa.domain.creditcard.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}