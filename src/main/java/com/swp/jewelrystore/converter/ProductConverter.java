package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.ProductMaterialEntity;
import com.swp.jewelrystore.entity.ProductGemEntity;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.ProductGemRepository;
import com.swp.jewelrystore.repository.ProductMaterialRepository;
import com.swp.jewelrystore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    @Autowired
    private ProductGemRepository productGemRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDTO toProductResponseDTO(ProductEntity productEntity) {
        ProductResponseDTO productResponseDTO = modelMapper.map(productEntity, ProductResponseDTO.class);
        // type of material
        List<ProductMaterialEntity> listProductMaterial = productMaterialRepository.findAllByProductId(productEntity.getId());
        List<String> materialNameList = new ArrayList<>();
        for (ProductMaterialEntity productMaterialEntity : listProductMaterial) {
            materialNameList.add(productMaterialEntity.getMaterial().getName());
        }
        String materialName = String.join(",", materialNameList);
        productResponseDTO.setMaterialName(materialName);
        // type of gem
        List<ProductGemEntity> listProductGem = productGemRepository.findAllByProductId(productEntity.getId());
        List<String> gemNameList = new ArrayList<>();
        for (ProductGemEntity productGemEntity : listProductGem) {
            gemNameList.add(productGemEntity.getGem().getGemName());
        }
        String gemName = String.join(",", gemNameList);
        productResponseDTO.setGemName(gemName);
        // category name
        productResponseDTO.setCategoryName(productEntity.getProductCategory().getCategoryName());
        //price
        productResponseDTO.setPrice(productRepository.calculateSellPrice(productEntity));
        // counterNo
        productResponseDTO.setCounterNo(productEntity.getCounter().getCounterNo());
        return productResponseDTO;
    }
}
