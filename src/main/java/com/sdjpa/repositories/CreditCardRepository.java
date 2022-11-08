package com.sdjpa.repositories;

import com.sdjpa.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}