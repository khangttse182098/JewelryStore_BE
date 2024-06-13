package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.model.request.ProductSearchRequestDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ProductRepositoryCustom {
    double calculateSellPrice(ProductEntity productEntity);
    double calculateBuyPrice(ProductEntity productEntity);
    double calculatePurchaseDiscountPrice(ProductEntity productEntity);
    List<ProductEntity> getAllProduct(ProductSearchRequestDTO productSearchRequestDTO);
}
