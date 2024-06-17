package com.swp.jewelrystore.api;


import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.enums.PurchaseDiscountRate;
import com.swp.jewelrystore.model.dto.DiscountDTO;
import com.swp.jewelrystore.model.response.DiscountResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IDiscountService;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @ApiImplicitParam(name = "code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isAvailable", dataType = "string", paramType = "query")
    })
    @GetMapping
    public List<DiscountResponseDTO> getDiscountInformation(@RequestParam Map<String, String> filter){
        return discountService.getDiscountInformation(filter);
    }

    @PostMapping("/information")
    public ResponseEntity<ResponseDTO> addOrUpdateDiscountInformation(@RequestBody @Valid DiscountDTO discountDTO
                                                ){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            discountService.addOrUpdateDiscountInformation(discountDTO);
            responseDTO.setMessage(SystemConstant.ADD_OR_UPDATE_DISCOUNT_SUCCESSFULLY);
            responseDTO.setData(discountDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @GetMapping("/purchase-discount")
    public DiscountResponseDTO purchaseDiscount(){
        DiscountResponseDTO discountResponseDTO = new DiscountResponseDTO();
        double value = PurchaseDiscountRate.PURCHASE_DISCOUNT_RATE.getValue();
        discountResponseDTO.setValue(String.valueOf(value));
        return discountResponseDTO;
    }





}
