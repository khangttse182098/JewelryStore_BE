package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.ProductResponseDTO;
import java.util.List;
import com.swp.jewelrystore.entity.ProductEntity;

public interface IProductService {
     List<ProductResponseDTO> getAllProduct(Long counterId);
     double calculateSellPrice(ProductEntity productEntity);

}
