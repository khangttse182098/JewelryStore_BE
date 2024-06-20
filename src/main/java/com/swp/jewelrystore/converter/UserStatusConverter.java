package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.constant.SystemConstant;

public class UserStatusConverter {
    public static String convertStatusFromNumberToText(Long number) {
         if (number == 1) {
             return SystemConstant.ON_STATUS;
         }
         return SystemConstant.OFF_STATUS;
    }

    public static Long convertStatusFromTextToNumber(String text) {
        if (text.equals(SystemConstant.ON_STATUS)) {
            return 1L;
        }
        return 0L;
    }

}
