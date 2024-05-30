package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductAPI {
    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getListProduct(@RequestParam(value="counter_id") Long counter_id){
        List<ProductResponseDTO> result = productService.getAllProduct(counter_id);
        return result;
    }

}
