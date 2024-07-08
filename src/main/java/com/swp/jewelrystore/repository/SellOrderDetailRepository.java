package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SellOrderDetailRepository extends JpaRepository<SellOrderDetailEntity, Long> {
    List<SellOrderDetailEntity> findBySellOrder(SellOrderEntity sellDetailEntity);
    void deleteByProductIn(List <ProductEntity> productEntities);
    List<SellOrderDetailEntity> findSellOrderDetailEntitiesByProductIsIn(List <ProductEntity> productEntities);
    List<SellOrderDetailEntity> findAllBySellOrderIn(List<SellOrderEntity> sellDetailEntityList);
    List<SellOrderDetailEntity> findBySellOrderId(Long id);
    SellOrderDetailEntity findByProductId(Long productId);
    List<SellOrderDetailEntity> findAllByProduct(ProductEntity product);
}
