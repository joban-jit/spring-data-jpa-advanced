package com.sdjpa;


import com.sdjpa.domain.*;
import com.sdjpa.repositories.CustomerRepository;
import com.sdjpa.repositories.OrderHeaderRepository;
import com.sdjpa.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Product product;

    private Customer customer;


    @BeforeEach
    void setup(){
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("test product");
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("New Customer");


        product = productRepository.saveAndFlush(newProduct);
        customer = customerRepository.saveAndFlush(newCustomer);
    }

    @Test
    void testSaveOrderWithOrderLine(){
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);
        orderHeader.setOrderStatus(OrderStatus.NEW);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);


        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");

        orderHeader.setOrderApproval(orderApproval);


//        orderHeader.setOrderLines(Collections.singleton(orderLine));
//        orderLine.setOrderHeader(orderHeader);

        orderHeader.addOrderLine(orderLine);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
//        customer.setOrders(Collections.singleton(savedOrder));


        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(1, savedOrder.getOrderLines().size());



        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());
        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertEquals(OrderStatus.NEW, fetchedOrder.getOrderStatus());
        assertNotNull(fetchedOrder.getCreatedDate());



    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);
        orderHeader.setOrderStatus(OrderStatus.NEW);


        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");

        orderHeader.setOrderApproval(orderApproval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
        orderHeaderRepository.flush();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getReferenceById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertEquals(OrderStatus.NEW, fetchedOrder.getOrderStatus());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

    @Test
    void testDeleteCascade(){
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("New Customer");
        orderHeader.setCustomer(customerRepository.save(customer));
        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(3);
        orderLine.setProduct(product);
        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        orderHeader.setOrderApproval(orderApproval);
        orderHeader.addOrderLine(orderLine);
        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);
        System.out.println("order saved and flushed");
        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();
        Long id = savedOrder.getId();
        assertThrows(EntityNotFoundException.class, ()->{
            OrderHeader order = orderHeaderRepository.getReferenceById(id);
            System.out.println(order);
        });
    }
}