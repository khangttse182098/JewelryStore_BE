package com.swp.jewelrystore.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GemWithPriceDTO {
   private Long gemId;
   private Double buyPrice;
   private Double sellPrice;
   private String effectDate;
   private String diamondName;
}
