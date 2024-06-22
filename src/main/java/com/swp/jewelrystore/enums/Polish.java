package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.List;

public enum Polish {
    EXCELLENT("Xuất sắc"),
    VERY_GOOD("Rất tốt"),
    GOOD("Tốt"),
    FAIR("Khá"),
    PR("Kém");

    private String polishName;
    Polish(String polishName) {
        this.polishName = polishName;
    }
    public String getPolishName() {
        return polishName;
    }

    public static List<String> getAllNameOfPolish(){
        List<String> listPolish = new ArrayList<>();
        for (Polish item : Polish.values()) {
            listPolish.add(item.getPolishName());
        }
        return listPolish;
    }
}
