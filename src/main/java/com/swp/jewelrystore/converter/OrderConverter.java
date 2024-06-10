package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductConverter productConverter;

    public InvoiceResponseDTO toInvoiceResponseDTO(SellOrderEntity sellOrderEntity) {
        InvoiceResponseDTO invoiceResponseDTO = modelMapper.map(sellOrderEntity, InvoiceResponseDTO.class);
        invoiceResponseDTO.setInvoiceCode(sellOrderEntity.getSellOrderCode());
        invoiceResponseDTO.setInvoiceType(SystemConstant.SELL);
        if(sellOrderEntity.getCustomer() != null) {
            invoiceResponseDTO.setCustomerName(sellOrderEntity.getCustomer().getFullName());
            invoiceResponseDTO.setCustomerId(sellOrderEntity.getCustomer().getId());
        }else{
            invoiceResponseDTO.setCustomerName(SystemConstant.NO_CUSTOMER_YET);
        }
        invoiceResponseDTO.setStaffName(sellOrderEntity.getUser().getFullName());
        int totalPrice = 0;
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        for(SellOrderDetailEntity sellOrderDetailEntity : sellOrderEntity.getSellOrderDetailEntities()){
            totalPrice += sellOrderDetailEntity.getPrice();
            ProductResponseDTO productResponseDTO = productConverter.toProductResponseDTO(sellOrderDetailEntity.getProduct());
            productResponseDTO.setPrice(sellOrderDetailEntity.getPrice());
            productResponseDTOList.add(productResponseDTO);
        }
        invoiceResponseDTO.setProductResponseDTOList(productResponseDTOList);
        invoiceResponseDTO.setTotalPrice(totalPrice);
        return invoiceResponseDTO;
    }
    public InvoiceResponseDTO toInvoiceResponseDTO(PurchaseOrderEntity purchaseOrderEntity) {
        InvoiceResponseDTO invoiceResponseDTO = modelMapper.map(purchaseOrderEntity, InvoiceResponseDTO.class);
        invoiceResponseDTO.setInvoiceCode(purchaseOrderEntity.getPurchaseOrderCode());
        invoiceResponseDTO.setInvoiceType(SystemConstant.REPURCHASE);
        if(purchaseOrderEntity.getCustomer() != null) {
            invoiceResponseDTO.setCustomerName(purchaseOrderEntity.getCustomer().getFullName());
            invoiceResponseDTO.setCustomerId(purchaseOrderEntity.getCustomer().getId());
        }
        else{
            invoiceResponseDTO.setCustomerName(SystemConstant.NO_CUSTOMER_YET);
        }
        invoiceResponseDTO.setStaffName(purchaseOrderEntity.getUser().getFullName());
        int totalPrice = 0;
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        for(PurchaseOrderDetailEntity purchaseOrderDetailEntity : purchaseOrderEntity.getPurchaseOrderDetailEntities()){
            totalPrice += purchaseOrderDetailEntity.getPrice();
            if(purchaseOrderDetailEntity.getProduct() != null){
                ProductResponseDTO productResponseDTO = productConverter.toProductResponseDTO(purchaseOrderDetailEntity.getProduct());
                productResponseDTO.setPrice(purchaseOrderDetailEntity.getPrice());
                productResponseDTOList.add(productResponseDTO);
            }

        }
        if(!productResponseDTOList.isEmpty()){
            invoiceResponseDTO.setProductResponseDTOList(productResponseDTOList);
        }
        invoiceResponseDTO.setTotalPrice(totalPrice);
        return invoiceResponseDTO;
    }
}
