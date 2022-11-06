package com.sdjpa.services;

import com.sdjpa.domain.Product;
import com.sdjpa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public Product updateQQH(Long id, BigDecimal quantityOnHand) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setQuantityOnHand(quantityOnHand);
        return productRepository.saveAndFlush(product);
    }
}
