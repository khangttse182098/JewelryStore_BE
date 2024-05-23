package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


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
    private CounterEntity counterEntity;

//    counter_id
    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_code")
    private String product_code;

    @Column(name = "product_image")
    private String product_image;

    @Column(name = "material_cost")
    private Double material_cost;

    @Column(name = "gem_cost")
    private Double gem_cost;

    @Column(name = "production_cost")
    private Double production_cost;

    @Column(name = "price_rate")
    private Double price_rate;

    @Column(name = "createddate")
    private Date createddate;

}
