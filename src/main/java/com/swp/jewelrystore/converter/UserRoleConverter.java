package com.swp.jewelrystore.converter;


public class UserRoleConverter {
    public static Long convertRoleFromTextToNumber(String role) {
        if (role.equalsIgnoreCase("Admin")) {
            return 1L;
        } else if (role.equalsIgnoreCase("Thu ngân")) {
            return 2L;
        } else if (role.equalsIgnoreCase("Nhân viên")) {
            return 3L;
        } else if (role.equalsIgnoreCase("Quản lý")){
            return 4L;
        }
        return null;
    }
}
