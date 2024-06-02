package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.model.dto.PurchaseInvoiceDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPurchaseOrderService {
    void addProductPurchaseOrder(PurchaseInvoiceDTO purchaseInvoiceDTO);
    List<PurchasePriceResponseDTO> showPurchasePrice(List<Long> productIds);
}
