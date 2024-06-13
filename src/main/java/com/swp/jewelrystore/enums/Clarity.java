package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum Clarity {
    FL("FL"),
    VVS1("VVS1"),
    VVS2("VVS2"),
    I1("I1"),
    I3("I3");

    private final String clarityName;

    Clarity(String clarityName) {
        this.clarityName = clarityName;
    }

    public String getClarityName() {
        return clarityName;
    }

    public static Map<String, String> clarity(){
        Map<String, String> listClarity = new LinkedHashMap<String, String>();
        for (Clarity item : Clarity.values()) {
            listClarity.put(item.toString(), item.getClarityName());
        }
        return listClarity;
    }

    public static List<String> getAllNameOfClarity(){
        List<String> listClarity = new ArrayList<>();
        for (Clarity item : Clarity.values()) {
            listClarity.add(item.getClarityName());
        }
        return listClarity;
    }
}
