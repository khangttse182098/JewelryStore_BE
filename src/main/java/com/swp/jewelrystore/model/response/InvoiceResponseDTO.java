package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InvoiceResponseDTO {
    private String invoiceCode;
    private Date createdDate;
    private String customerName;
    private String invoiceType;
    private String staffName;
    private int totalPrice;
    private String status;
    private Long customerId;
    List<ProductResponseDTO> productResponseDTOList;
    List<MaterialResponseDTO> materialResponseDTOList;
    List<DiamondCriteriaResponseDTO> diamondCriteriaResponseDTOS;
}
