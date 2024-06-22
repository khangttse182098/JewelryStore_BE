package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GemResponseDTO {
    private Long id;
    private String gemName;
    private Double buyPrice;
    private Double sellPrice;
    private String effectDate;
}
