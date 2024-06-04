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

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private GemRepository gemRepository;

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
            ProductResponseDTO productResponseDTO = productConverter.toProductResponseDTO(productEntity);
            for (SellOrderDetailEntity sellOrderDetailEntity : sellOrderDetailEntities) {
                if (sellOrderDetailEntity.getProduct().getId() == productEntity.getId()) {
                    productResponseDTO.setPrice(Double.valueOf(sellOrderDetailEntity.getPrice()));
                }
            }
            productResponseDTOList.add(productResponseDTO);
        }
        return productResponseDTOList;
    }

    @Override
    public void addOrUpdateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        // Product Category
        ProductCategoryEntity productCategoryEntity = productCategoryRepository.findByIdIs(productDTO.getProductCategoryId());
        productEntity.setProductCategory(productCategoryEntity);
        if(productCategoryEntity.getSubCategoryType().equals("Trang sức")){
            productEntity.getProductCategory().setSubCategoryType(productDTO.getSubCategoryType());
        }
        // Counter
        CounterEntity counterEntity = counterRepository.findCounterEntityById(productDTO.getCounterId());
        productEntity.setCounter(counterEntity);
        // Material
        MaterialEntity materialEntity = materialRepository.findMaterialEntityById(productDTO.getMaterialId());
        // Product Image
        productEntity.setProductImage("image.png");
        // Save product
        productRepository.save(productEntity);
        // Product Material
        ProductMaterialEntity productMaterialEntity = new ProductMaterialEntity();
        productMaterialEntity.setMaterial(materialEntity);
        productMaterialEntity.setProduct(productEntity);
        // Gem
        GemEntity gemEntity = gemRepository.findGemEntityById(productDTO.getGemId());
        // ProductGem
        ProductGemEntity productGemEntity = new ProductGemEntity();
        productGemEntity.setGem(gemEntity);
        productGemEntity.setProduct(productEntity);
        productGemRepository.save(productGemEntity);
    }










}
