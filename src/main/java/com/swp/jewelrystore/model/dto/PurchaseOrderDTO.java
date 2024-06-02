package com.swp.jewelrystore.model.dto;

import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseOrderDTO {
    private String fullName;
    private String address;
    private String phoneNumber;
    private Long userId;
    private List<MaterialResponseDTO> materials;
    private String purchaseOrderStatus;
}
