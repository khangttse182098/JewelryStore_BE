package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import com.swp.jewelrystore.service.ISellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
@RequiredArgsConstructor
public class OrderAPI {

     private final IPurchaseOrderService purchaseOrderService;
     private final ISellOrderService sellOrderService;

     @PostMapping("/sell")
     public void addSellOrderInformation(@RequestBody InvoiceDTO invoiceDTO){
          sellOrderService.addSellOrderInformation(invoiceDTO);
     }

     // sửa lại object truyen vào
     @PostMapping("/purchase")
     public List<MaterialResponseDTO> addPurchaseOrderInformation(@RequestBody CriteriaDTO criteriaDTO){
          return purchaseOrderService.addPurchaseOrderInformation(criteriaDTO);
     }

     @PostMapping("/purchase-invoice")
     public void addPurchaseInvoiceInformation(@RequestBody PurchaseOrderDTO purchaseOrderDTO){
          purchaseOrderService.addPurchaseInvoiceInformation(purchaseOrderDTO);
     }
}
