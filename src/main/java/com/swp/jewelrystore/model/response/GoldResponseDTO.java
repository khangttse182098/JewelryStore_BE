package com.swp.jewelrystore.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldResponseDTO {
      private String goldName;
      private Long buyPrice;
      private Long sellPrice;
      private String effectDate;
}
