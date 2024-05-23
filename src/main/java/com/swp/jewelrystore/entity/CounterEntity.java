package com.swp.jewelrystore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "counter")
@Getter
@Setter
public class CounterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counter_id")
    private Long id;

    @Column(name = "counter_no")
    private String counter_no;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "counterEntity", fetch = FetchType.LAZY)
    private List<ProductEntity> productEntities = new ArrayList<>();

    @OneToMany(mappedBy = "counterEntity", fetch = FetchType.LAZY)
    private List<AssignmentCounterEntity> assignmentCounterEntities = new ArrayList<>();
}
