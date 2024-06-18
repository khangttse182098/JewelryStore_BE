package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.ProductCategoryResponseDTO;
import com.swp.jewelrystore.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sub-category-type")
@CrossOrigin
public class ProductCategoryAPI {
    @Autowired
    private IProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategoryResponseDTO> getCategoryType() {
        List<ProductCategoryResponseDTO> productCategoryResponseDTOS = productCategoryService.findByCategoryName("Trang sức");
        return productCategoryResponseDTOS;
    }
}
