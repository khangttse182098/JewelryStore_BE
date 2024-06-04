package com.swp.jewelrystore.repository;



import com.swp.jewelrystore.entity.ProductGemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductGemRepository extends JpaRepository<ProductGemEntity, Long> {
    List<ProductGemEntity> findAllByProductId(Long id);
    void deleteAllByProductIdIn(List<Long> ids);
}
