package com.inventoryservice.util;

import com.inventoryservice.model.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public static List<SimpleGrantedAuthority> getUserInfo(String role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String roleName = role.substring(role.lastIndexOf("_") + 1, role.length() - 2);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
        authorities.add(authority);
        return authorities;
    }
}
