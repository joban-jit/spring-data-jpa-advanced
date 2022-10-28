package com.sdjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderLine extends BaseEntity{

    private Integer quantityOrdered;
    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;


    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (!(o instanceof OrderLine orderLine)) return false;
        if (!super.equals(o)) return false;

        if (getQuantityOrdered() != null ? !getQuantityOrdered().equals(orderLine.getQuantityOrdered()) : orderLine.getQuantityOrdered() != null)
            return false;
        if (getOrderHeader() != null ? !getOrderHeader().equals(orderLine.getOrderHeader()) : orderLine.getOrderHeader() != null)
            return false;
        if (getProduct() != null ? !getProduct().equals(orderLine.getProduct()) : orderLine.getProduct() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(orderLine.getCreatedDate()) : orderLine.getCreatedDate() != null)
            return false;
        return getLastModifiedDate() != null ? getLastModifiedDate().equals(orderLine.getLastModifiedDate()) : orderLine.getLastModifiedDate() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getQuantityOrdered() != null ? getQuantityOrdered().hashCode() : 0);
        result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        return result;
    }
}
