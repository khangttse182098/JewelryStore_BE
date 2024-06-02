package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.CustomerEntity;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.dto.InvoiceDTO;
import com.swp.jewelrystore.repository.*;
import com.swp.jewelrystore.service.ISellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SellOrderService implements ISellOrderService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final SellOrderRepository sellOrderRepository;
    private final SellOrderDetailRepository sellOrderDetailRepository;


    @Override
    public void addSellOrderInformation(InvoiceDTO invoiceDTO) {
        CustomerEntity checkCustomer = customerRepository.findByFullNameOrPhoneNumber(invoiceDTO.getFullName(), invoiceDTO.getPhoneNumber());
        List<ProductEntity> listProduct = productRepository.findAllByIdIn(invoiceDTO.getProductId());
        // customer already existed
        // Sellordercode: SEL001
        if (checkCustomer != null ){
            SellOrderEntity sellOrder = new SellOrderEntity();
            sellOrder.setUser(userRepository.findById(invoiceDTO.getUserId()).get());
            sellOrder.setCustomer(checkCustomer);
            sellOrder.setSellOrderCode("SEL007");
            sellOrder.setStatus(invoiceDTO.getSellOrderStatus());
            sellOrderRepository.save(sellOrder);
            int i = 0;
            for (ProductEntity item : listProduct) {
                SellOrderDetailEntity sellOrderDetail = new SellOrderDetailEntity();
                sellOrderDetail.setSellOrder(sellOrder);
                sellOrderDetail.setProduct(item);
                sellOrderDetail.setPrice(invoiceDTO.getPrice().get(i));
                i++;
                sellOrderDetailRepository.save(sellOrderDetail);
            }
        } // customer not existed
        else {
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setFullName(invoiceDTO.getFullName());
            newCustomer.setPhoneNumber(invoiceDTO.getPhoneNumber());
            customerRepository.save(newCustomer);
            SellOrderEntity sellOrder = new SellOrderEntity();
            sellOrder.setUser(userRepository.findById(invoiceDTO.getUserId()).get());
            sellOrder.setCustomer(newCustomer);
            sellOrder.setSellOrderCode("SEL009");
            sellOrder.setStatus(invoiceDTO.getSellOrderStatus());
            sellOrderRepository.save(sellOrder);
            int i = 0;
            for (ProductEntity item : listProduct) {
                SellOrderDetailEntity sellOrderDetail = new SellOrderDetailEntity();
                sellOrderDetail.setSellOrder(sellOrder);
                sellOrderDetail.setProduct(item);
                sellOrderDetail.setPrice(invoiceDTO.getPrice().get(i));
                i++;
                sellOrderDetailRepository.save(sellOrderDetail);
            }
        }
    }
}
