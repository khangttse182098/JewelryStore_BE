package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.SellOrderRepositoryCustom;
import com.swp.jewelrystore.entity.SellOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SellOrderRepository extends JpaRepository<SellOrderEntity, Long> , SellOrderRepositoryCustom, CrudRepository<SellOrderEntity, Long> {
    SellOrderEntity findBySellOrderCodeIs(String sellOrderCode);
    List<SellOrderEntity> findByCustomer_IdAndStatusNot(Long id, String status);
    List<SellOrderEntity> findSellOrderEntitiesByStatusIsIn(List<String> status);
    List<SellOrderEntity> findByUser_IdAndStatusNot(Long id, String status);

    @Query(value = "SELECT * FROM sellorder WHERE MONTH(created_date) = :month AND YEAR(created_date) = :year", nativeQuery = true)
    List<SellOrderEntity> findByCreatedDateMonth(@Param("month") int month, @Param("year") int year);

}
