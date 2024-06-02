package com.swp.jewelrystore.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "productgem")
public class ProductGemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_gem_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name="gem_id")
    private GemEntity gem;
}

