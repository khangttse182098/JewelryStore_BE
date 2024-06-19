package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.DiscountEntity;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.ProductMaterialEntity;
import com.swp.jewelrystore.entity.ProductGemEntity;
import com.swp.jewelrystore.enums.PurchaseDiscountRate;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.DiscountRepository;
import com.swp.jewelrystore.repository.ProductGemRepository;
import com.swp.jewelrystore.repository.ProductMaterialRepository;
import com.swp.jewelrystore.repository.ProductRepository;
import com.swp.jewelrystore.service.IStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IStorageService storageService;
    @Autowired
    private DiscountRepository discountRepository;

    public ProductResponseDTO toProductResponseDTO(ProductEntity productEntity) {
        ProductResponseDTO productResponseDTO = modelMapper.map(productEntity, ProductResponseDTO.class);
        // type of material
        List<ProductMaterialEntity> listProductMaterial = productMaterialRepository.findAllByProductId(productEntity.getId());
        List<String> materialNameList = new ArrayList<>();
        for (ProductMaterialEntity productMaterialEntity : listProductMaterial) {
            materialNameList.add(productMaterialEntity.getMaterial().getName());
            productResponseDTO.setMaterialWeight(productMaterialEntity.getWeight());
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
        // subcategorytype
        if(productEntity.getProductCategory().getCategoryName().equals("Trang sức")){
            productResponseDTO.setSubCategoryType(productEntity.getProductCategory().getSubCategoryType());
        }
        //price
        double price = productRepository.calculateSellPrice(productEntity);
        productResponseDTO.setPrice(price);
        // discountPrice
        Map<String, String> filter = new HashMap<>();
        filter.put("isAvailable", "true");
        DiscountEntity discountEntity = discountRepository.searchWithRequired(filter).get(0);
        productResponseDTO.setDiscountPrice(price * discountEntity.getValue() / 100);
        // counterNo
        productResponseDTO.setCounterNo(productEntity.getCounter().getCounterNo());
        // image
        String productImage = "http://mahika.foundation:8080/swp/api/file/download/"+ productEntity.getProductCode() +".jpg";
        productResponseDTO.setProductImage(productImage);
        return productResponseDTO;

    }
}
