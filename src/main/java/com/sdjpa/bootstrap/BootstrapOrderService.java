package com.sdjpa.bootstrap;

import com.sdjpa.domain.OrderHeader;
import com.sdjpa.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BootstrapOrderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Transactional
    public void readOrderData(){
        OrderHeader orderHeader = orderHeaderRepository.findById(400L).get();
        orderHeader.getOrderLines().forEach(ol->{
            System.out.println(ol.getProduct().getDescription());
            System.out.println(ol.getProduct().getCategories().size());
            ol.getProduct().getCategories().forEach(cat->{
                System.out.println(cat.getDescription());
            });
        });
    }
}
