package com.sdjpa.bootstrap;

import com.sdjpa.domain.Customer;
import com.sdjpa.domain.OrderHeader;
import com.sdjpa.repositories.CustomerRepository;
import com.sdjpa.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BootstrapOrderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;


    @Autowired
    private CustomerRepository customerRepository;

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

//    @Transactional // if we use txn here it would be one txn and version would be 1, but changes would reflect
    // if we don't use @Transactional then version each save method would have different txn
    // so in the end we will have version=3

    public void customerOptimisticLocking() {
        Customer customer = new Customer();
        customer.setCustomerName("Testing version");
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("Version is "+savedCustomer.getVersion());

        savedCustomer.setCustomerName("Testing version 2");
        Customer savedCustomer2 = customerRepository.save(savedCustomer);
        System.out.println("Version2 is "+savedCustomer2.getVersion());

        savedCustomer2.setCustomerName("Testing version 3");
        Customer savedCustomer3 = customerRepository.save(savedCustomer2);
        System.out.println("Version3 is "+savedCustomer3.getVersion());

//        customerRepository.delete(savedCustomer3);
    }

//    public void orderHeaderOptimisticLocking(){
//        OrderHeader orderHeader = new OrderHeader();
//        orderHeader.set
//    }


}
