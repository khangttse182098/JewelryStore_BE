package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategoryEntity productCategory;

    @ManyToOne
    @JoinColumn(name = "counter_id")
    private CounterEntity counter;


    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "material_cost")
    private Double materialCost;

    @Column(name = "gem_cost")
    private Double gemCost;

    @Column(name = "production_cost")
    private Double productionCost;

    @Column(name = "price_rate")
    private Double priceRate;

    @Column(name = "createddate")
    private Date createdDate;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductGemEntity> productGemEntities;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<PurchaseOrderDetailEntity> purchaseOrderDetailEntities;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<SellOrderDetailEntity> sellOrderDetailEntities;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductMaterialEntity> productMaterialEntities;

}
