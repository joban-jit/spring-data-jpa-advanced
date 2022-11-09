package com.sdjpa.domain.pan;

import com.sdjpa.domain.CreditCardConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credit_card_pan")
@Getter
@Setter
@NoArgsConstructor
public class CreditCardPAN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;

    private Long creditCardId;

}
