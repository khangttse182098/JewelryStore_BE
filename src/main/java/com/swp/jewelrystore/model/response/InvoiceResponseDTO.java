package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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





}
