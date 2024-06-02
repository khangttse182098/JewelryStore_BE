package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ProductRepositoryCustom {
    double calculateSellPrice(ProductEntity productEntity);
    double calculateBuyPrice(ProductEntity productEntity);
    List<ProductEntity> getAllProduct(Map <String, String> params);
}
