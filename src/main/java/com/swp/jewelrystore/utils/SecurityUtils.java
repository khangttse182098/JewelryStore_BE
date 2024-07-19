package com.swp.jewelrystore.utils;

import com.swp.jewelrystore.model.dto.MyUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static MyUserDetail getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal.toString());
//        MyUserDetail userDetail = new MyUserDetail();
//        userDetail.setUsername(principal.());
        System.out.println(principal.toString());
        if(principal instanceof MyUserDetail) {
            return (MyUserDetail) principal;
        }else{
            MyUserDetail myUserDetail = new MyUserDetail();
            myUserDetail.setUsername(principal.toString());
            return myUserDetail;
        }

    }

    public static List<String> getAuthorities() {
        List<String> results = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>)(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        for (GrantedAuthority authority : authorities) {
            results.add(authority.getAuthority());
        }
        return results;
    }
}
