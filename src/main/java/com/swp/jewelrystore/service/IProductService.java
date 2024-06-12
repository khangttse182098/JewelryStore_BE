package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.ProductDTO;
import com.swp.jewelrystore.model.request.ProductSearchRequestDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;

import java.util.List;
import java.util.Map;

import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.stereotype.Service;

public interface IProductService {
     List<ProductResponseDTO> getAllProduct(ProductSearchRequestDTO productSearchRequestDTO);
    List<ProductResponseDTO>getProductBySellOrderCode(String sellOrderCode);
    void addOrUpdateProduct(ProductDTO productDTO);
    void deleteByIdsIn(List<Long> ids);
}
