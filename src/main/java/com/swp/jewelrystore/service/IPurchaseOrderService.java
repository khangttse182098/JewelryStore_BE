package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;

import java.util.List;

public interface IPurchaseOrderService {
    List<MaterialResponseDTO> addPurchaseOrderInformation(CriteriaDTO criteriaDTO);
    void addPurchaseInvoiceInformation(PurchaseOrderDTO purchaseOrderDTO);
}
