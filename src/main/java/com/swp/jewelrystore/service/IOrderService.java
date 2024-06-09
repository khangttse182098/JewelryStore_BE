package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.InvoiceResponseDTO;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    List<InvoiceResponseDTO> getAllOrder(Map<String,String> params);
    List<InvoiceResponseDTO> getPaidAndDeliveredSellOrder();
    List<InvoiceResponseDTO> getPaidAndReceivedPurchaseOrder();
}
