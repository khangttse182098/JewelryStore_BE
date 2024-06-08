package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.custom.DiscountRepositoryCustom;
import com.swp.jewelrystore.entity.DiscountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class DiscountRepositoryImpl implements DiscountRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final DateTimeConverter dateTimeConverter;
    @Override
    public List<DiscountEntity> searchWithRequired(Map<String, String> filter) {
        StringBuilder sql = new StringBuilder(" SELECT * FROM discount ");
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Format the current date and time
        String currentDate = currentDateTime.format(formatter);
        LocalDateTime current = LocalDateTime.parse(currentDate, formatter);
        for(Map.Entry<String, String> param : filter.entrySet()){
             if (param.getKey().contains("time")){
                 if (param.getValue().contains("Hôm nay")){
                     StringTokenizer st = new StringTokenizer(currentDate, " ");
                     where.append(" AND SUBSTRING_INDEX(start_date, ' ', 1) IN ('" + st.nextToken() + "') ");
                 } else if (param.getValue().contains("7 ngày trước")){
                     LocalDateTime previous = current.minusDays(7);
                     StringTokenizer tokenizer = new StringTokenizer(previous.format(formatter), " ");
                     where.append(" AND SUBSTRING_INDEX(start_date, ' ', 1) IN ('" + tokenizer.nextToken() + "')");
                 } else if (param.getValue().contains("Tháng trước")){
                     LocalDateTime previous = current.minusMonths(1);
                     String previousMonth = String.valueOf(previous.getMonthValue());
                     where.append(" AND MONTH(start_date) IN ('" + previousMonth + "')");
                 } else if (param.getValue().contains("Tháng nay")){
                     String thisMonth = String.valueOf(current.getMonthValue());
                     where.append(" AND MONTH(start_date) IN ('" + thisMonth + " ')");
                 } else if (param.getValue().contains("Năm trước")) {
                     LocalDateTime previousMonthDateTime = current.minusYears(1);
                     String previousYear = String.valueOf(previousMonthDateTime.getYear());
                     where.append(" AND YEAR(start_date) IN ('" + previousYear + " ')");
                 } else if (param.getValue().contains("Năm nay")) {
                     String thisYear = String.valueOf(current.getYear());
                     where.append(" AND YEAR(start_date) IN ('" + thisYear + "')");
                 }
             } else if (param.getKey().contains("code")){
                     where.append(" AND code LIKE '%" + param.getValue() + "%'");
             }
        }
        String startDate = filter.get("startDate");
        String endDate = filter.get("endDate");
        if (startDate != null ){
            StringTokenizer tokenizer = new StringTokenizer(startDate, " ");
            where.append(" AND SUBSTRING_INDEX(start_date, ' ', 1) IN ('" + dateTimeConverter.convertToDateCompareToDB(tokenizer.nextToken()) + "')");
        }
        if (endDate != null ){
            StringTokenizer tokenizer = new StringTokenizer(endDate, " ");
            where.append(" AND SUBSTRING_INDEX(end_date, ' ', 1) IN ('" + dateTimeConverter.convertToDateCompareToDB(tokenizer.nextToken()) + "')");
        }
        sql.append(where);
        System.out.println(sql);
        Query query = entityManager.createNativeQuery(sql.toString(), DiscountEntity.class);
        return query.getResultList();
    }
}
