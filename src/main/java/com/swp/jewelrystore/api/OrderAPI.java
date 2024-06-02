package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderAPI {
    @Autowired
    IOrderService orderService;
    @GetMapping
    public List<InvoiceResponseDTO> getAllOrder(){
        List<InvoiceResponseDTO> invoiceList =orderService.getAllOrder();
        return invoiceList;
    }
}
