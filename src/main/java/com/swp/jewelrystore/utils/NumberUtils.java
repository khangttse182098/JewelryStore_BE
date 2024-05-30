package com.swp.jewelrystore.utils;

public class NumberUtils {
    public static boolean isLong(String value) {
        if(value == null)return false;
        try {
            Long number = Long.parseLong(value);
        }
        catch(NumberFormatException ex) {
            return false;
        }
        return true;
    }
}