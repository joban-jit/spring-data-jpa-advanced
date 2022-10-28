package com.sdjpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderApproval extends BaseEntity{

    private String approvedBy;

    @OneToOne
    @JoinColumn(name = "order_header_id")
    private OrderHeader orderHeader;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof OrderApproval that)) return false;
        if (!super.equals(o)) return false;

        return getApprovedBy() != null ? getApprovedBy().equals(that.getApprovedBy()) : that.getApprovedBy() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getApprovedBy() != null ? getApprovedBy().hashCode() : 0);
        return result;
    }
}
