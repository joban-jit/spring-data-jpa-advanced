package com.sdjpa.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 12/5/21.
 */
@Entity
@AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")
        )
@AttributeOverride(
                name = "shippingAddress.city",
                column = @Column(name = "shipping_city")
        )
@AttributeOverride(
                name = "shippingAddress.state",
                column = @Column(name = "shipping_state")
        )
@AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")
        )
@AttributeOverride(
                name = "billToAddress.address",
                column = @Column(name = "bill_to_address")
        )
@AttributeOverride(
                name = "billToAddress.city",
                column = @Column(name = "bill_to_city")
        )
@AttributeOverride(
                name = "billToAddress.state",
                column = @Column(name = "bill_to_state")
        )
@AttributeOverride(
                name = "billToAddress.zipCode",
                column = @Column(name = "bill_to_zip_code")
        )

@Getter
@Setter
public class OrderHeader extends BaseEntity{



    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billToAddress;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Fetch(FetchMode.SELECT)
    private OrderApproval orderApproval;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<OrderLine> orderLines;

    @Version
    private Integer version;

    public void addOrderLine(OrderLine orderLine){
        if(null == orderLines){
            orderLines = new HashSet<>();
        }
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }

    public void setOrderApproval(OrderApproval orderApproval) {
        this.orderApproval = orderApproval;
        orderApproval.setOrderHeader(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHeader that)) return false;
        if (!super.equals(o)) return false;

        if (getShippingAddress() != null ? !getShippingAddress().equals(that.getShippingAddress()) : that.getShippingAddress() != null)
            return false;
        if (getBillToAddress() != null ? !getBillToAddress().equals(that.getBillToAddress()) : that.getBillToAddress() != null)
            return false;
        if (getOrderStatus() != that.getOrderStatus()) return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getLastModifiedDate() != null ? !getLastModifiedDate().equals(that.getLastModifiedDate()) : that.getLastModifiedDate() != null)
            return false;
        return getOrderLines() != null ? getOrderLines().equals(that.getOrderLines()) : that.getOrderLines() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getShippingAddress() != null ? getShippingAddress().hashCode() : 0);
        result = 31 * result + (getBillToAddress() != null ? getBillToAddress().hashCode() : 0);
        result = 31 * result + (getOrderStatus() != null ? getOrderStatus().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getOrderLines() != null ? getOrderLines().hashCode() : 0);
        return result;
    }
}
