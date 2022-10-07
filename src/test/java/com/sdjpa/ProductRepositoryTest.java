package com.sdjpa;

import com.sdjpa.domain.Product;
import com.sdjpa.domain.ProductStatus;
import com.sdjpa.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

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
}
