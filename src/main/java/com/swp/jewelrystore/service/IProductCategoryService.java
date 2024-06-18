package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.ProductCategoryEntity;
import com.swp.jewelrystore.model.response.ProductCategoryResponseDTO;

import java.util.List;

public interface IProductCategoryService {
    List<ProductCategoryResponseDTO> findByCategoryName(String categoryName);
    List<String> getCategoryName();
}
