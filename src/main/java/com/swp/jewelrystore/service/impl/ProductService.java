package com.swp.jewelrystore.service.impl;


import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.converter.ProductConverter;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.dto.ProductDTO;
import com.swp.jewelrystore.model.request.ProductSearchRequestDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.*;
import com.swp.jewelrystore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Transactional
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final CounterRepository counterRepository;
    private final ProductGemRepository productGemRepository;
    private final ProductMaterialRepository productMaterialRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final SellOrderDetailRepository sellOrderDetailRepository;
    private final SellOrderRepository sellOrderRepository;
    private final MaterialRepository materialRepository;
    private final GemRepository gemRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Override
    public List<ProductResponseDTO> getAllProduct(ProductSearchRequestDTO productSearchRequestDTO) {
        List<ProductEntity> productEntities = productRepository.getAllProduct(productSearchRequestDTO);
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
        List<SellOrderDetailEntity> sellOrderDetailEntities = sellOrderEntity.getSellOrderDetailEntities();
        List<ProductEntity> productEntities = new ArrayList<>();
        for (SellOrderDetailEntity sellOrderDetailEntity : sellOrderDetailEntities) {
            if(sellOrderDetailEntity.getProduct().getPurchaseOrderDetailEntities().isEmpty()){
                productEntities.add(sellOrderDetailEntity.getProduct());
            }
        }
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
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity();
        if(productDTO.getProductCategoryName().equals(SystemConstant.JEWELRY)){
            productCategoryEntity = productCategoryRepository.findByCategoryNameAndSubCategoryType(productDTO.getProductCategoryName(), productDTO.getSubCategoryType());
            productEntity.setProductCategory(productCategoryEntity);
        }else{
            productCategoryEntity = productCategoryRepository.findByCategoryName(productDTO.getProductCategoryName());
            productEntity.setProductCategory(productCategoryEntity);
        }
        // Counter
        CounterEntity counterEntity = counterRepository.findCounterEntityById(productDTO.getCounterId());
        productEntity.setCounter(counterEntity);
        // Material
        MaterialEntity materialEntity = materialRepository.findMaterialEntityById(productDTO.getMaterialId());
        // Product Image
        productEntity.setProductImage(productDTO.getProductImage());
        // Save product
        productRepository.save(productEntity);
        // Product Material
        if(materialEntity != null && productEntity != null){
            ProductMaterialEntity productMaterialEntity = new ProductMaterialEntity();
            productMaterialEntity.setMaterial(materialEntity);
            productMaterialEntity.setProduct(productEntity);
            productMaterialEntity.setWeight(productDTO.getMaterialWeight());
            productMaterialRepository.save(productMaterialEntity);
        }

        // Gem
        GemEntity gemEntity = gemRepository.findGemEntityById(productDTO.getGemId());
        // ProductGem
        if(materialEntity != null && productEntity != null){
            ProductGemEntity productGemEntity = new ProductGemEntity();
            productGemEntity.setGem(gemEntity);
            productGemEntity.setProduct(productEntity);
            productGemRepository.save(productGemEntity);
        }
    }

    @Override
    public void deleteByIdsIn(List<Long> ids) {
        productMaterialRepository.deleteAllByProductIdIn(ids);
        productGemRepository.deleteAllByProductIdIn(ids);
        productRepository.deleteByIdIn(ids);
    }

}
