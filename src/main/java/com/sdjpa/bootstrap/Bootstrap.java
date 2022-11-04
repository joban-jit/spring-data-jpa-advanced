package com.sdjpa.bootstrap;

import com.sdjpa.domain.OrderHeader;
import com.sdjpa.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private BootstrapOrderService bootstrapOrderService;

 /*   @Transactional
    public void readOrderData(){
        OrderHeader orderHeader = orderHeaderRepository.findById(400L).get();
        orderHeader.getOrderLines().forEach(ol->{
            System.out.println(ol.getProduct().getDescription());
            System.out.println(ol.getProduct().getCategories().size());
            ol.getProduct().getCategories().forEach(cat->{
                System.out.println(cat.getDescription());
            });
        });
    }*/


    @Override
    public void run(String... args) throws Exception {
//        readOrderData();
        bootstrapOrderService.readOrderData();
    }
}
