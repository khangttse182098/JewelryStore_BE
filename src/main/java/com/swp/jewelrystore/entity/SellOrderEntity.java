package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellorder")
public class SellOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sell_order_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @OneToMany(mappedBy = "sellOrder", fetch = FetchType.LAZY)
    private List<SellOrderDetailEntity> sellOrderDetailEntities = new ArrayList<>();

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "sell_order_code")
    private String sellOrderCode;

    @Column(name = "status")
    private String status;

}