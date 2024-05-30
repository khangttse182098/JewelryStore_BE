package com.swp.jewelrystore.api;


import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import com.swp.jewelrystore.service.ISellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     public void addPurchaseOrderInformation(@RequestBody InvoiceDTO invoiceDTO){
          purchaseOrderService.addPurchaseOrderInformation(invoiceDTO);
     }
}
