package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.List;

public enum Symmetry {
    EXCELLENT("Xuất sắc"),
    VERY_GOOD("Rất tốt"),
    GOOD("Tốt"),
    FAIR("Khá"),
    PR("Kém");

    private String symmetryName;
    Symmetry(String symmetryName) {
        this.symmetryName = symmetryName;
    }
    public String getSymmetryName() {
        return symmetryName;
    }

    public static List<String> getAllNameOfSymmetry(){
        List<String> listSymmetry = new ArrayList<>();
        for (Symmetry item : Symmetry.values()) {
            listSymmetry.add(item.getSymmetryName());
        }
        return listSymmetry;
    }
}
