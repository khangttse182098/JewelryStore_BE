package com.swp.jewelrystore.controller;

import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @MessageMapping("order/cashier-page")
    @SendTo("/topic/orders")
    public List<InvoiceResponseDTO> getPaidAndReceivedPurchaseOrder() throws Exception {
        logger.info("Received request for /order/cashier-page");
        List<InvoiceResponseDTO> invoiceList = orderService.getPaidAndReceivedPurchaseOrder();
        Thread.sleep(1000);
        logger.info("Returning invoice list");
        return invoiceList;
    }
}
