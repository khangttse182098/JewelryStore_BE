package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.DiscountDTO;
import com.swp.jewelrystore.model.response.DiscountResponseDTO;
import com.swp.jewelrystore.service.IDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
@CrossOrigin
@RequiredArgsConstructor
public class DiscountAPI {

    private final IDiscountService discountService;

    @GetMapping
    public List<DiscountResponseDTO> getDiscountInformation(){
        return discountService.getDiscountInformation();
    }

    @PostMapping("/add")
    public String addDiscountInformation(@RequestBody DiscountDTO discountDTO){
        discountService.addDiscountInformation(discountDTO);
        return "Add new discount information successfully";
    }



}
