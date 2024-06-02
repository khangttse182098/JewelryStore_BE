package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.OrderConverter;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import com.swp.jewelrystore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class OrderService implements IOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    SellOrderRepository sellOrderRepository;

    @Autowired
    OrderConverter orderConverter;

    @Override
    public List<InvoiceResponseDTO> getAllOrder() {
        List<InvoiceResponseDTO> invoiceResponseDTOs = new ArrayList<>();
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findAll();
        for(PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
            invoiceResponseDTOs.add(orderConverter.toInvoiceResponseDTO(purchaseOrderEntity));
        }
        List<SellOrderEntity> sellOrderEntitiesv = sellOrderRepository.findAll();
        for(SellOrderEntity sellOrderEntity : sellOrderEntitiesv) {
            invoiceResponseDTOs.add(orderConverter.toInvoiceResponseDTO(sellOrderEntity));
        }
        return invoiceResponseDTOs;
    }
}
