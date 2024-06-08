package com.swp.jewelrystore.model.response;

import java.util.Date;
import java.util.List;

public class InvoiceDetailResponseDTO {
    List<ProductResponseDTO> products;
    private String invoiceCode;
    private Date createdDate;
    private String customerName;
    private String invoiceType;
    private String staffName;
    private int totalPrice;
    private String status;
}
