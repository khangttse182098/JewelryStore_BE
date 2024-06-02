package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "gem")
public class GemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gem_id")
    private Long id;

    @Column(name = "gem_code")
    private String gemCode;

    @Column(name = "gem_name")
    private String gemName;

    @Column(name = "origin")
    private String origin;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "carat_weight")
    private Double caratWeight;

    @Column(name = "cut")
    private String cut;

    @Column(name = "proportions")
    private String proportions;

    @Column(name = "polish")
    private String polish;

    @Column(name = "symmetry")
    private String symmetry;

    @Column(name = "fluorescence")
    private String fluorescence;

    @Column(name = "active")
    private Long active;

    @OneToMany(mappedBy = "gem", fetch = FetchType.LAZY)
    private List<ProductGemEntity> productGemEntities;


}