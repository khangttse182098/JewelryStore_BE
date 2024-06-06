package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.model.dto.PurchaseInvoiceDTO;
import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import org.springframework.stereotype.Service;
import com.swp.jewelrystore.model.dto.CriteriaDTO;
import com.swp.jewelrystore.model.dto.PurchaseOrderDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;

import java.util.List;
@Service
public interface IPurchaseOrderService {
    CriteriaResponseDTO showMaterialInvoice(CriteriaDTO criteriaDTO);
    void addPurchaseInvoiceInformation(PurchaseOrderDTO purchaseOrderDTO);
    void addProductPurchaseOrder(PurchaseInvoiceDTO purchaseInvoiceDTO);
    List<PurchasePriceResponseDTO> showPurchasePrice(List<Long> productIds);

}
