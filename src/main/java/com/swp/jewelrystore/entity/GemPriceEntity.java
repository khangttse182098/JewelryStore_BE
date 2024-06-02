package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "gemprice")
public class GemPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gem_price_id", nullable = false)
    private Long id;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "clarity", nullable = false)
    private String clarity;

    @Column(name = "carat_weight", nullable = false)
    private Double caratWeight;

    @Column(name = "cut", nullable = false)
    private String cut;

    @Column(name = "buy_price", nullable = false)
    private Double buyPrice;

    @Column(name = "sell_price", nullable = false)
    private Double sellPrice;

    @Column(name = "effect_date", nullable = false)
    private Date effectDate;

}