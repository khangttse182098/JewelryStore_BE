package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WarrantyDTO {
    private String customerName;
    private String phoneNumber;
    private List<Long> listProductId;
}
