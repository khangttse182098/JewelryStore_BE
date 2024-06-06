package com.swp.jewelrystore.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerInvoiceDTO {
     private String sellOrderCode;
     private Date createdDate;
     private Double price;
     private String status;
}
