package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.dto.PurchaseInvoiceDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.repository.ProductRepository;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

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
    public CriteriaResponseDTO showMaterialGemPrice(@RequestBody CriteriaDTO criteriaDTO){
        return purchaseOrderService.showMaterialInvoice(criteriaDTO);
    }
    @PostMapping("/no-invoice")
    public String addPurchaseInvoiceNoInvoice(@RequestBody PurchaseOrderDTO purchaseOrderDTO){
        purchaseOrderService.addPurchaseInvoiceInformation(purchaseOrderDTO);
        return "Add successfully";
    }


}
