package com.sdjpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product extends BaseEntity{
    private String description;

    private BigDecimal quantityOnHand = BigDecimal.ONE;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        if (!super.equals(o)) return false;

        if (getDescription() != null ? !getDescription().equals(product.getDescription()) : product.getDescription() != null)
            return false;
        return getProductStatus() == product.getProductStatus();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getProductStatus() != null ? getProductStatus().hashCode() : 0);
        return result;
    }
}
