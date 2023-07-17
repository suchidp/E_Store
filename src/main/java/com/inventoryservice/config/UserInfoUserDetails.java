package com.inventoryservice.config;

import com.inventoryservice.model.SimpleGrantedAuthority;
import com.inventoryservice.util.UserUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class UserInfoUserDetails implements UserDetails {

    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(String role) {
        List<SimpleGrantedAuthority> authorities = UserUtil.getUserInfo(role);
        this.authorities = new ArrayList<>(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
