package com.sdjpa.domain;

import com.sdjpa.interceptors.EncryptedString;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EncryptedString
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getCreditCardNumber() != null ? !getCreditCardNumber().equals(that.getCreditCardNumber()) : that.getCreditCardNumber() != null)
            return false;
        if (getCvv() != null ? !getCvv().equals(that.getCvv()) : that.getCvv() != null) return false;
        return getExpirationDate() != null ? getExpirationDate().equals(that.getExpirationDate()) : that.getExpirationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCreditCardNumber() != null ? getCreditCardNumber().hashCode() : 0);
        result = 31 * result + (getCvv() != null ? getCvv().hashCode() : 0);
        result = 31 * result + (getExpirationDate() != null ? getExpirationDate().hashCode() : 0);
        return result;
    }
}
