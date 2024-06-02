package com.swp.jewelrystore.repository;


import com.swp.jewelrystore.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    MaterialEntity findMaterialEntityById(long id);
      MaterialEntity findByName(String name);
}
