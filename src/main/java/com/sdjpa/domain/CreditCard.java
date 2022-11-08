package com.sdjpa.domain;

import com.sdjpa.custom_annotations.EncryptedString;
import com.sdjpa.listeners.PostLoadListener;
import com.sdjpa.listeners.PreInsertListener;
import com.sdjpa.listeners.PreUpdateListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@EntityListeners(value = {CreditCardJPACallback.class}) disabled jpa callbacks
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @EncryptedString // commented this as we won't use our annotation with jpa converters
    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @PrePersist
    public void prePresistCallback(){
        System.out.println("JPA PrePresist Callback was called");
    }

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
