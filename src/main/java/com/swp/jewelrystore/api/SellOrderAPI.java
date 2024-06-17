package com.swp.jewelrystore.api;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IProductService;
import com.swp.jewelrystore.service.ISellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sell-order")
@CrossOrigin
@RequiredArgsConstructor
public class SellOrderAPI {

    private final IProductService productService;
    private final ISellOrderService sellOrderService;


    @GetMapping
    public List<ProductResponseDTO> getProductBySellOrderCode(@RequestParam String sellOrderCode ){
        List<ProductResponseDTO> result = productService.getProductBySellOrderCode(sellOrderCode);
        return result;
    }

    @PostMapping("/information")
    public ResponseEntity<ResponseDTO> addSellOrderInformation(@RequestBody @Valid InvoiceDTO invoiceDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            sellOrderService.addSellOrderInformation(invoiceDTO);
            responseDTO.setData(invoiceDTO);
            responseDTO.setMessage(SystemConstant.ADD_SELL_ORDER_SUCCESSFULLY);
            return ResponseEntity.ok().body(responseDTO);
        } catch(Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
