package com.sdjpa.services;


import com.sdjpa.domain.Product;

import java.math.BigDecimal;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateQQH(Long id, BigDecimal quantityOnHand);
}
