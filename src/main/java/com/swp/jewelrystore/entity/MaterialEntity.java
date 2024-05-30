package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "material")
public class MaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="material_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<MaterialPriceEntity> materialPriceEntities;

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<ProductMaterialEntity> productMaterialEntities;
}
