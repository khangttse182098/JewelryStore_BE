package com.swp.jewelrystore.utils;

import com.swp.jewelrystore.converter.DateTimeConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SearchOrderUtils {
    public static DateTimeConverter dateTimeConverter;
    public static void queryWhereNormal(StringBuilder sql, Map<String, String> params) {
        List<String> specialWhere = Arrays.asList("startDate", "endDate", "time", "code");
        for(Map.Entry<String, String> param : params.entrySet()){
            if(!specialWhere.contains(param.getKey())) {
                if (NumberUtils.isLong(param.getValue())) {
                    sql.append(" AND " + param.getKey() + " = " + param.getValue().trim());
                } else if (StringUtils.check(param.getValue())) {
                    sql.append(" AND " + param.getKey() + " LIKE '%" + param.getValue().trim() + "%'");
                }
            }
        }
    }
    public static void queryWhereSpecial(StringBuilder sql, Map<String, String> params) {
        if(params.get("startDate") != null){
            String startDateString = params.get("startDate").trim();
            if(StringUtils.check(startDateString)){
                Date startDate = dateTimeConverter.convertToDateTimeDTO(startDateString);
                sql.append(" AND created_date >= " + startDate.toString());
            }
        }
        if(params.get("endDate") != null){
            String endDateString = params.get("endDate").trim();
            if(StringUtils.check(endDateString)){
                Date endDate = dateTimeConverter.convertToDateTimeDTO(endDateString);
                sql.append(" AND created_date <= " + endDate.toString());
            }
        }
        if(params.get("time") != null){
            String time = params.get("time").trim();
            if(StringUtils.check(time)){
                switch (time){
                    case "7days" :
                        sql.append(" AND created_date >= CURDATE() - INTERVAL 7 DAY");
                        break;
                    case "30days" :
                        sql.append(" AND created_date >= CURDATE() - INTERVAL 30 DAY");
                        break;
                    case "12months" :
                        sql.append(" AND created_date >= CURDATE() - INTERVAL 12 MONTH");
                        break;
                    case "alltime":
                        break;
                }
            }
        }
    }
}
