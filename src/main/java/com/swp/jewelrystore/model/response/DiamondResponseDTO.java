package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiamondResponseDTO {
    private String name;
    private Double buyPrice;
    private Double sellPrice;
    private String effectDate;
}
