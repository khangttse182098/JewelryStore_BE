package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum Origin {
    NATURAL("Tự nhiên"),
    ARTIFICIAL("Nhân tạo");

    private final String originName;

    Origin(String originName) {
        this.originName = originName;
    }

    public String getOriginName(){
        return originName;
    }

    public static Map<String, String> origin(){
        Map<String, String> listOrigin = new LinkedHashMap<String, String>();
        for (Origin item : Origin.values()) {
            listOrigin.put(item.toString(), item.getOriginName());
        }
        return listOrigin;
    }

    public static List<String> getAllNameOfOrigin(){
        List<String> listOrigin = new ArrayList<>();
        for (Origin item : Origin.values()) {
            listOrigin.add(item.getOriginName());
        }
        return listOrigin;
    }
}
