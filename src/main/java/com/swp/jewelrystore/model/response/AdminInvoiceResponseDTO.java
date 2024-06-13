package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInvoiceResponseDTO {
     private Long id;
     private String goodsCode;
     private String customerCode;
     private String staffName;
     private String goodsType; // bán - mua
     private String status;
     private String createdDate;
}
