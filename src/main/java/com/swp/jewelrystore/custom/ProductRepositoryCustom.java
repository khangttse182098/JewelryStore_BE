package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ProductRepositoryCustom {
    double calculateSellPrice(ProductEntity productEntity);
    List<ProductEntity> getAllProduct(Map <String, String> params);
}
