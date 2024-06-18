package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.ProductCategoryEntity;
import com.swp.jewelrystore.model.response.ProductCategoryResponseDTO;
import com.swp.jewelrystore.repository.ProductCategoryRepository;
import com.swp.jewelrystore.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class ProductCategoryService implements IProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategoryResponseDTO> findByCategoryName(String categoryName) {
        List<ProductCategoryResponseDTO> productCategoryResponseDTOS = new ArrayList<>();
        List<ProductCategoryEntity> productCategoryEntities = productCategoryRepository.findProductCategoryEntitiesByCategoryName(categoryName);
        for (ProductCategoryEntity productCategoryEntity : productCategoryEntities) {
            ProductCategoryResponseDTO productCategoryResponseDTO = new ProductCategoryResponseDTO();
            productCategoryResponseDTO.setProductCategoryId(productCategoryEntity.getId());
            productCategoryResponseDTO.setSubCategoryType(productCategoryEntity.getSubCategoryType());
            productCategoryResponseDTOS.add(productCategoryResponseDTO);
        }
        return productCategoryResponseDTOS;
    }

    @Override
    public List<String> getCategoryName() {
        List<ProductCategoryEntity> productCategoryEntities = productCategoryRepository.findAll();
        List<String> categoryNames = new ArrayList<>();
        for (ProductCategoryEntity productCategoryEntity : productCategoryEntities) {
            if(!categoryNames.contains(productCategoryEntity.getCategoryName())) {
                categoryNames.add(productCategoryEntity.getCategoryName());
            }
        }
        return categoryNames;
    }
}
