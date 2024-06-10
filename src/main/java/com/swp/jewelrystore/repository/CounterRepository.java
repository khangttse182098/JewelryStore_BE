package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.CounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<CounterEntity, Long> {
    CounterEntity findCounterEntityById(Long id);
}
