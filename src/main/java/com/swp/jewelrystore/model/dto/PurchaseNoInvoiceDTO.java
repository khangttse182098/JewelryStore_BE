package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PurchaseNoInvoiceDTO {
    private Long userId;
    private String materialWeight;
    private Long materialId;
    private String origin;
    private String color;
    private String clarity;
    private Double caratWeight;
    private String cut;
}
