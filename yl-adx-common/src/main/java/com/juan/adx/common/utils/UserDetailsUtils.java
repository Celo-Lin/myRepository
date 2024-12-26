package com.juan.adx.common.utils;

import com.juan.adx.common.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/26 15:59
 * @Description: 用户详情工具类
 * @Version: V1.0
 */
public class UserDetailsUtils {
    /**
     * 获取当前用户
     */
    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        }
        return userDetails;
    }

    /**
     * 获取当前用户用户名
     */
    public static String getCurrentUsername() {
        UserDetails userDetails = getCurrentUser();
        return userDetails == null ? null : userDetails.getUsername();
    }
}
