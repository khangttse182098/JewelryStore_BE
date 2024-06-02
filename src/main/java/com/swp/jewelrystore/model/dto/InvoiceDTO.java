package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceDTO {
    private List<Long> productId;
    private String fullName;
    private String phoneNumber;
    private Long userId;
    private String sellOrderStatus;
    private List<Long> price;
}
