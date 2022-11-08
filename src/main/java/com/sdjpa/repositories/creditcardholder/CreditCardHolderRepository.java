package com.sdjpa.repositories.creditcardholder;

import com.sdjpa.domain.creditcardholder.CreditCardHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
}
