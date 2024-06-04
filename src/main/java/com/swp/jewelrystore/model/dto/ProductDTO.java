package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// FE gui ve ID cua dah
public class ProductDTO {
     private Long id;
     private String productCode;
     private String productName;
     private String productCategoryName; // loại sản phẩm: kim cương, trang sức
     private Long materialId;
     private Double materialWeight;
     private Long counterId;
     private Double materialCost;
     private Double productionCost;
     private Double priceRate;
     private Double gemCost;
     private String subCategoryType; // danh muc: nhẫn, dây chuyen, ...
     private Long gemId;
     private String productImage;

}
