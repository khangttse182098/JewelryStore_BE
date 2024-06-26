package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.converter.OrderConverter;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import com.swp.jewelrystore.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SellOrderRepository sellOrderRepository;
    private final OrderConverter orderConverter;

    @Override
    public List<InvoiceResponseDTO> getAllOrder(Map<String, String> params) {
        List<InvoiceResponseDTO> invoiceResponseDTOs = new ArrayList<>();
        List<SellOrderEntity> sellOrderEntities = sellOrderRepository.findAllSellOrder(params);
        for(SellOrderEntity sellOrderEntity : sellOrderEntities) {
            invoiceResponseDTOs.add(orderConverter.toInvoiceResponseDTO(sellOrderEntity));
        }
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findAllPurchaseOrder(params);
        for(PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
            invoiceResponseDTOs.add(orderConverter.toInvoiceResponseDTO(purchaseOrderEntity));
        }
        Collections.sort(invoiceResponseDTOs, new Comparator<InvoiceResponseDTO>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
            @Override
            public int compare(InvoiceResponseDTO i1, InvoiceResponseDTO i2) {
                try {
                    Date date1 = dateFormat.parse(i1.getCreatedDate());
                    Date date2 = dateFormat.parse(i2.getCreatedDate());
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return invoiceResponseDTOs;
    }

    @Override
    public List<InvoiceResponseDTO> getPaidAndDeliveredSellOrder() {
        List<InvoiceResponseDTO> invoiceResponseDTOs = new ArrayList<>();
        List<String> status = new ArrayList<>();
        status.add(SystemConstant.PAID);
        status.add(SystemConstant.DELIVERED);
        List<SellOrderEntity> sellOrderEntities = sellOrderRepository.findSellOrderEntitiesByStatusIsIn(status);
        for(SellOrderEntity sellOrderEntity : sellOrderEntities) {
            invoiceResponseDTOs.add(orderConverter.toInvoiceResponseDTO(sellOrderEntity));
        }
        return invoiceResponseDTOs;
    }

    @Override
    public List<InvoiceResponseDTO> getPaidAndReceivedPurchaseOrder() {
        List<InvoiceResponseDTO> invoiceResponseDTOs = new ArrayList<>();
        List<String> status = new ArrayList<>();
        status.add("Đã thanh toán");
        status.add("Đã nhận hàng");
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findPurchaseOrderEntitiesByStatusIsIn(status);
        for(PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
            invoiceResponseDTOs.add(orderConverter.toInvoiceResponseDTO(purchaseOrderEntity));
        }
        return invoiceResponseDTOs;
    }
}
