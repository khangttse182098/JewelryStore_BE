package com.swp.jewelrystore.service.impl;


import com.swp.jewelrystore.converter.ProductConverter;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.dto.ProductDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.*;
import com.swp.jewelrystore.service.IProductService;
import org.modelmapper.ModelMapper;
import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {


    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private ProductGemRepository productGemRepository;

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private SellOrderDetailRepository sellOrderDetailRepository;
    @Autowired
    private SellOrderRepository sellOrderRepository;

    @Override
    public List<ProductResponseDTO> getAllProduct(Map<String, String > params) {
        List<ProductEntity> productEntities = productRepository.getAllProduct(params);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            productResponseDTO = productConverter.toProductResponseDTO(productEntity);
            productResponseDTOs.add(productResponseDTO);
        }
        return productResponseDTOs;
    }

    @Override
    public List<ProductResponseDTO> getProductBySellOrderCode(String sellOrderCode) {
        SellOrderEntity sellOrderEntity = sellOrderRepository.findBySellOrderCodeIs(sellOrderCode);
        List<SellOrderDetailEntity> sellOrderDetailEntities = sellOrderDetailRepository.findBySellOrder(sellOrderEntity);
        List<ProductEntity> productEntities = productRepository.findBySellOrderDetailEntitiesIn(sellOrderDetailEntities);
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            productResponseDTOList.add(productConverter.toProductResponseDTO(productEntity));
        }
        return productResponseDTOList;
    }

    @Override
    public void addOrUpdateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        // Add product category
//        if(productDTO.getCategoryName().equals)
//        ProductCategoryEntity productCategoryEntity = productCategoryRepository.findByCategoryName(productDTO.getCategoryName());
        //
//        productEntity.setProductCategory();
    }









}
