package com.swp.jewelrystore.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "productmaterial")
public class ProductMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_material_id")
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @ManyToOne
    @JoinColumn(name="material_id")
    private MaterialEntity material;

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product;
}