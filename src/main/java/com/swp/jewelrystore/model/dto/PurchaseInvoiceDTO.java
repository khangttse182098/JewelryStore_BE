package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PurchaseInvoiceDTO {
    private String fullName;
    private String phoneNumber;
    private String address;
    private Long userId;
    private List<Long> productId;
}
