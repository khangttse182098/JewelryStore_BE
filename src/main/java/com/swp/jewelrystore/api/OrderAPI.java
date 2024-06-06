package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import com.swp.jewelrystore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import com.swp.jewelrystore.service.ISellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
@RequiredArgsConstructor
public class OrderAPI {

    @Autowired
    private IOrderService orderService;
    @GetMapping
    public List<InvoiceResponseDTO> getAllOrder(@RequestParam Map<String, String> params){
        List<InvoiceResponseDTO> invoiceList = orderService.getAllOrder(params);
        return invoiceList;
    }

     private final IPurchaseOrderService purchaseOrderService;
     private final ISellOrderService sellOrderService;

     @PostMapping("/sell")
     public String addSellOrderInformation(@RequestBody InvoiceDTO invoiceDTO){
          sellOrderService.addSellOrderInformation(invoiceDTO);
          return "Added sell order successfully";
     }

    @PostMapping("/purchase")
    public CriteriaResponseDTO showMaterialInvoice(@RequestBody CriteriaDTO criteriaDTO){
        return purchaseOrderService.showMaterialInvoice(criteriaDTO);
    }

     @PostMapping("/purchase-invoice")
     public String addPurchaseInvoiceInformation(@RequestBody PurchaseOrderDTO purchaseOrderDTO){
          purchaseOrderService.addPurchaseInvoiceInformation(purchaseOrderDTO);
          return "Add successfully";
     }
}
