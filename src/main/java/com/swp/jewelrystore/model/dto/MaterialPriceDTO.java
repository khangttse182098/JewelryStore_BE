package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MaterialPriceDTO {
    private Long materialId;
    private Double buyPrice;
    private Double sellPrice;
    private String effectDate;
}
