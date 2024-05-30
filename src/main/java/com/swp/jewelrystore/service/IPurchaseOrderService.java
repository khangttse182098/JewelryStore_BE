package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.InvoiceDTO;

public interface IPurchaseOrderService {
    void addPurchaseOrderInformation(InvoiceDTO invoiceDTO);
}
