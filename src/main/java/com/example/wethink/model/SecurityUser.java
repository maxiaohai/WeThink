package com.example.wethink.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
public class SecurityUser implements Serializable, UserDetails {
    private String id;
    private String email;
    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            //Spring Security默认的角色名是“ROLE_xxx”的格式”
            //authorities.add(new SimpleGrantedAuthority(“ROLE_” + role.getName()));
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
        */
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
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
