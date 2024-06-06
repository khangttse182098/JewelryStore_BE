package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PurchaseInvoiceDTO {
    private Long userId;
    private List<Long> productId;
    private String sellOrderCode;
}
