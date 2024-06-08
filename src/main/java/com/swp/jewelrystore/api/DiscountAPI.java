package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.DiscountDTO;
import com.swp.jewelrystore.model.response.DiscountResponseDTO;
import com.swp.jewelrystore.service.IDiscountService;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/discount")
@CrossOrigin
@RequiredArgsConstructor
public class DiscountAPI {

    private final IDiscountService discountService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "time", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "startDate", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "code", dataType = "string", paramType = "query")
    })
    @GetMapping
    public List<DiscountResponseDTO> getDiscountInformation(@RequestParam Map<String, String> filter){
        return discountService.getDiscountInformation(filter);
    }

    @PostMapping()
    public String addOrUpdateDiscountInformation(@RequestBody DiscountDTO discountDTO){
        discountService.addOrUpdateDiscountInformation(discountDTO);
        return "Add or update discount information successfully";
    }



}
