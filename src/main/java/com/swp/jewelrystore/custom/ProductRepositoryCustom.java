package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.stereotype.Service;


public interface ProductRepositoryCustom {
    double calculateSellPrice(ProductEntity productEntity);
}
