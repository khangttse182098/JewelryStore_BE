package com.swp.jewelrystore.service;


import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements IProductService{

    private final CounterRepository counterRepository;
    private final ProductGemRepository productGemRepository;
    private final ProductMaterialRepository productMaterialRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;


    @Override
    public List<ProductResponseDTO> getAllProduct(Long counterId) {
        // find a counter base on counter_id
        CounterEntity counter = counterRepository.findById(counterId).get();
        List<ProductEntity> listProduct = counter.getProductEntities();
        List<ProductResponseDTO> result = new ArrayList<>();
        for (ProductEntity productEntity : listProduct) {
            ProductResponseDTO productResponseDTO = modelMapper.map(productEntity, ProductResponseDTO.class);
            // type of gem
            List<ProductGemEntity> listProductGem = productGemRepository.findAllByProductId(productEntity.getId());
            String gemName = listProductGem.stream().map(ProductGemEntity::getGem).map(GemEntity::getGemName).collect(Collectors.joining(", "));
            productResponseDTO.setGemName(gemName);
            // type of material
            List<ProductMaterialEntity> listProductMaterial = productMaterialRepository.findAllByProductId(productEntity.getId());
            String materialName = listProductMaterial.stream().map(ProductMaterialEntity::getMaterial).map(MaterialEntity::getName).collect(Collectors.joining(", "));
            productResponseDTO.setMaterialName(materialName);
            productResponseDTO.setPrice(calculateSellPrice(productEntity));
            // category name
            productResponseDTO.setCategoryName(productEntity.getProductCategory().getCategoryName());
            result.add(productResponseDTO);
        }
        return result;
    }
    public double calculateSellPrice(ProductEntity productEntity) {
        return productRepository.calculateSellPrice(productEntity);
    }


}
