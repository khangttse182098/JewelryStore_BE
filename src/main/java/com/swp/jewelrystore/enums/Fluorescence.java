package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.List;

public enum Fluorescence {
    NONE("Không đến trơ"),
    FAINT("Yếu"),
    MEDIUM("Trung bình"),
    STRONG("Mạnh"),
    VERY_STRONG("Rất mạnh");

    private final String fluorescenceName;
    Fluorescence(String fluorescenceName) {
        this.fluorescenceName = fluorescenceName;
    }
    public String getFluorescenceName() {
        return fluorescenceName;
    }

    public static List<String> getAllNameOfFluorescence(){
        List<String> listFluorescence = new ArrayList<>();
        for (Fluorescence item : Fluorescence.values()) {
            listFluorescence.add(item.getFluorescenceName());
        }
        return listFluorescence;
    }
}
