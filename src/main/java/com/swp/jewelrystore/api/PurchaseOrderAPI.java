package com.swp.jewelrystore.api;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.dto.PurchaseInvoiceDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api/purchase-order")
@CrossOrigin
@RequiredArgsConstructor
public class PurchaseOrderAPI {

    private final IPurchaseOrderService purchaseOrderService;

    @PostMapping("/product-price")
    public List<PurchasePriceResponseDTO> showProductPurchasePrice(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO ){
        List<PurchasePriceResponseDTO> result = purchaseOrderService.showPurchasePrice(purchaseInvoiceDTO.getProductId());
        return result;
    }
    @PostMapping("/have-invoice")
    public String addPurchaseOrderHavingInvoice(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO ){
        purchaseOrderService.addProductPurchaseOrder(purchaseInvoiceDTO);
        return "Add product successfully!";
    }
    @PostMapping("/material-gem-price")
    public ResponseEntity<CriteriaResponseDTO> showMaterialGemPrice(@RequestBody CriteriaDTO criteriaDTO){
         return purchaseOrderService.showMaterialInvoice(criteriaDTO);
    }
    @PostMapping("/no-invoice")
    public ResponseEntity<ResponseDTO> addPurchaseInvoiceNoInvoice(@RequestBody @Valid PurchaseOrderDTO purchaseOrderDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            purchaseOrderService.addPurchaseInvoiceInformation(purchaseOrderDTO);
            responseDTO.setMessage(SystemConstant.ADD_SELL_ORDER_NO_INVOICE);
            responseDTO.setData(purchaseOrderDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
