package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.CounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterRepository extends JpaRepository<CounterEntity, Long> {

}
