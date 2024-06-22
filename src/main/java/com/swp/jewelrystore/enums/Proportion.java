package com.swp.jewelrystore.enums;

import java.util.ArrayList;
import java.util.List;

public enum Proportion {
    CROWN_ANGLE("Góc vương miện"),
    PAVILION_ANGLE("Góc pavilion"),
    GIRDLE_THIN("Đai mỏng"),
    CULET_NONE("Không có chóp đáy");


    private String proportionName;
    Proportion(String proportionName) {
        this.proportionName = proportionName;
    }
    public String getProportionName() {
        return proportionName;
    }

    public static List<String> getAllNameOfProportion(){
        List<String> listProportion = new ArrayList<>();
        for (Proportion item : Proportion.values()) {
            listProportion.add(item.getProportionName());
        }
        return listProportion;
    }
}
