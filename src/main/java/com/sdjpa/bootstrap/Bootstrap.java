package com.sdjpa.bootstrap;

import com.sdjpa.domain.Customer;
import com.sdjpa.domain.OrderHeader;
import com.sdjpa.domain.Product;
import com.sdjpa.domain.ProductStatus;
import com.sdjpa.repositories.CustomerRepository;
import com.sdjpa.repositories.OrderHeaderRepository;
import com.sdjpa.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private BootstrapOrderService bootstrapOrderService;

    @Autowired
    private ProductService productService;

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


    private void updateProduct(){
        Product product = new Product();
        product.setDescription("My Product");
        product.setProductStatus(ProductStatus.NEW);

        Product savedProduct = productService.saveProduct(product);

        Product savedProduct2 = productService.updateQQH(savedProduct.getId(), BigDecimal.valueOf(25));
        System.out.println(savedProduct2.getQuantityOnHand());
    }



    @Override
    public void run(String... args) throws Exception {
//        readOrderData();
//        bootstrapOrderService.readOrderData();
//        bootstrapOrderService.customerOptimisticLocking();
            updateProduct();
    }




}
