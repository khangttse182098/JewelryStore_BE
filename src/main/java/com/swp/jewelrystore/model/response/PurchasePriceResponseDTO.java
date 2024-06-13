package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchasePriceResponseDTO {
    private double purchasePrice;
    private Long productId;
    private double discountPrice;
}
