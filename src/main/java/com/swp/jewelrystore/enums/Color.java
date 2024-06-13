package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum Color {
    D("D"),
    H("H"),
    L("L"),
    P("P"),
    Z("Z");

    private final String colorName;

    Color (String colorName) {
        this.colorName = colorName;
    }
    public String getColorName() {
        return colorName;
    }

    public static Map<String, String> color(){
        Map<String, String> listColor = new LinkedHashMap<String, String>();
        for (Color item : Color.values()) {
            listColor.put(item.toString(), item.getColorName());
        }
        return listColor;
    }

    public static List<String> getAllNameOfColor(){
        List<String> listColor = new ArrayList<>();
        for (Color item : Color.values()) {
            listColor.add(item.getColorName());
        }
        return listColor;
    }
}
