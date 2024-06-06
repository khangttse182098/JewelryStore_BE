package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/diamond-price")
@CrossOrigin
@RequiredArgsConstructor
public class DiamondPriceAPI {
     private final IDiamondPriceService diamondPriceService;

     @GetMapping
     public List<DiamondResponseDTO> getDiamondPrice(){
         return diamondPriceService.getDiamondPrice();
     }
}
