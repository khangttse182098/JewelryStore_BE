package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.response.InvoiceResponseDTO;

import java.util.List;

public interface IOrderService {
    List<InvoiceResponseDTO> getAllOrder();
}
