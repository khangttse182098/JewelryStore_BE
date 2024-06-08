package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.ProductRepositoryCustom;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class calculateSellPriceTest {

    @Autowired
    private ProductRepository productRepository;

    ProductEntity productEntityId1 = productRepository.findByIdIs(1L) ;
    @Test
    void calculateSellPrice() {
        assertEquals(80, productRepository.calculateSellPrice(productEntityId1));
    }
}