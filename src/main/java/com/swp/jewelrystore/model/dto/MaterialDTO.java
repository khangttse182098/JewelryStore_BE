package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MaterialDTO {
    private Long materialId;
    private Double buyPrice;
    private Double sellPrice;
    private Date effectDate;
}
