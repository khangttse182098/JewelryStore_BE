package com.swp.jewelrystore.model.response;

import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.dto.CustomerInvoiceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDetailDTO {
     private Long id;
     private String fullName;
     private String gender;
     private String address;
     private String phoneNumber;
     private int quantityOrder;
     private Double expense;
     private Double expenseAverage;
     private List<CustomerInvoiceDTO> listCustomerInvoice;
}
