package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum Cut {
    EX("EX"),
    VG("VG"),
    GD("GD"),
    FR("FR"),
    PR("PR");

    private final String cutName;
     Cut(String cutName) {
        this.cutName = cutName;
    }
    public String getCutName() {
        return cutName;
    }

    public static Map<String, String> cut(){
        Map<String, String> listCut = new LinkedHashMap<String, String>();
        for (Cut item : Cut.values()) {
            listCut.put(item.toString(), item.getCutName());
        }
        return listCut;
    }

    public static List<String> getAllNameOfCut(){
        List<String> listCut = new ArrayList<>();
        for (Cut item : Cut.values()) {
            listCut.add(item.getCutName());
        }
        return listCut;
    }
}
