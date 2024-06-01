package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.model.dto.PurchaseInvoiceDTO;
import com.swp.jewelrystore.model.response.PurchasePriceResponseDTO;
import com.swp.jewelrystore.repository.ProductRepository;
import com.swp.jewelrystore.repository.PurchaseOrderDetailRepository;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import com.swp.jewelrystore.repository.UserRepository;
import com.swp.jewelrystore.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Override
    public void addProductPurchaseOrder(PurchaseInvoiceDTO purchaseInvoiceDTO) {
        PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
        purchaseOrderEntity.setPurchaseOrderCode("PUR002");
        purchaseOrderEntity.setUser(userRepository.findByIdIs(purchaseInvoiceDTO.getUserId()));
        purchaseOrderEntity.setStatus("Chưa thanh toán");
        purchaseOrderRepository.save(purchaseOrderEntity);
        for (Long id : purchaseInvoiceDTO.getProductId()) {
            PurchaseOrderDetailEntity purchaseOrderDetailEntity = new PurchaseOrderDetailEntity();
            ProductEntity productEntity = productRepository.findByIdIs(id);
            purchaseOrderDetailEntity.setPurchaseOrder(purchaseOrderEntity);
            purchaseOrderDetailEntity.setProduct(productEntity);
            purchaseOrderDetailEntity.setPrice(productRepository.calculateBuyPrice(productEntity));
            purchaseOrderDetailRepository.save(purchaseOrderDetailEntity);
        }
    }

    @Override
    public List<PurchasePriceResponseDTO> showPurchasePrice(List<Long> productIds) {
        List<PurchasePriceResponseDTO> purchasePriceResponseDTOList = new ArrayList<>();
        for (Long productId : productIds) {
            ProductEntity productEntity = productRepository.findByIdIs(productId);
            PurchasePriceResponseDTO purchasePriceResponseDTO = new PurchasePriceResponseDTO();
            purchasePriceResponseDTO.setProductId(productId);
            purchasePriceResponseDTO.setPurchasePrice(productRepository.calculateBuyPrice(productEntity));
            purchasePriceResponseDTOList.add(purchasePriceResponseDTO);
        }
        return purchasePriceResponseDTOList;
    }
}
