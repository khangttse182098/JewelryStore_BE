package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sell-order")
@CrossOrigin

public class SellOrderAPI {
    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getProductBySellOrderCode(@RequestParam String sellOrderCode ){
        List<ProductResponseDTO> result = productService.getProductBySellOrderCode(sellOrderCode);
        return result;
    }
}
