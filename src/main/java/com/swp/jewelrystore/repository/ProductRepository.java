package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.ProductRepositoryCustom;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {
    ProductEntity findByIdIs(Long productId);
    List<ProductEntity> findByIdIsIn(List<Long> ids);
    List<ProductEntity> findAllByIdIn(List<Long> productIds);
    void deleteByIdIn(List<Long> ids);
}
