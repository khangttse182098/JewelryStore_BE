package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.dto.PurchaseInvoiceDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.repository.ProductRepository;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/purchase-order")
@CrossOrigin

public class PurchaseOrderAPI {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    IPurchaseOrderService purchaseOrderService;

    @PostMapping("/product-price")
    public List<PurchasePriceResponseDTO> getProductPurchasePrice(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO ){
        List<PurchasePriceResponseDTO> result = purchaseOrderService.showPurchasePrice(purchaseInvoiceDTO.getProductId());
        return result;
    }
    @PostMapping
    public String addPurchaseOrder(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO ){
        purchaseOrderService.addProductPurchaseOrder(purchaseInvoiceDTO);
        return "Add product successfully!";
    }
   // @PostMapping("/material-gem-price")
    // public List<PurchasePriceResponseDTO> getMaterialGemPurchasePrice(@RequestBody PurchaseNoInvoiceDTO purchaseNoInvoiceDTO ){
//        List<PurchasePriceResponseDTO> result = purchaseOrderService.showPurchasePrice(purchaseNoInvoiceDTO.get);
//        return result;
//    }

}
