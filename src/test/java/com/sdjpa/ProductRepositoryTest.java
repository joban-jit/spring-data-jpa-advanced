package com.sdjpa;

import com.sdjpa.domain.Product;
import com.sdjpa.domain.ProductStatus;
import com.sdjpa.repositories.ProductRepository;
import com.sdjpa.services.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {ProductService.class})
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setDescription("My Product");
        product.setProductStatus(ProductStatus.NEW);

        Product savedProduct = productRepository.save(product);

        Product fetchedProduct = productRepository.getReferenceById(savedProduct.getId());

        assertNotNull(fetchedProduct);
        assertNotNull(fetchedProduct.getDescription());
        assertNotNull(fetchedProduct.getCreatedDate());
        assertNotNull(fetchedProduct.getLastModifiedDate());
    }
    @Test
    @Rollback(value = false)
    void testSaveProductWithQuantity() {
        Product product = new Product();
        product.setDescription("My Product");
        product.setProductStatus(ProductStatus.NEW);

        Product savedProduct = productService.saveProduct(product);

        Product savedProduct2 = productService.updateQQH(savedProduct.getId(), BigDecimal.valueOf(25));
        System.out.println(savedProduct2.getQuantityOnHand());

    }

    @Test
    void testGetCategory(){
        Optional<Product> product1 = productRepository.findByDescription("Product 1");
        assertNotNull(product1.get());
        assertNotNull(product1.get().getCategories());

    }






}
