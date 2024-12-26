package com.juan.adx.common.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 18:06
 * @Description: 用户信息对象
 * @Version: V1.0
 */
@Data
public class CustomUserDetails implements UserDetails {

    private String userId;
    private String username;
    private String roleIds;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String userId, String username, String roleIds, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.roleIds = roleIds;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // 不需要密码
    }

    @Override
    public String getUsername() {
        return username;
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
