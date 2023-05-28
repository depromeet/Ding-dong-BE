package com.dingdong.api.config.security;


import com.dingdong.core.exception.SecurityContextNotFoundException;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

public class SecurityUtils {

    private static SimpleGrantedAuthority swagger = new SimpleGrantedAuthority("ROLE_SWAGGER");

    private static List<SimpleGrantedAuthority> notUserAuthority = List.of(swagger);

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw SecurityContextNotFoundException.EXCEPTION;
        }

        if (authentication.isAuthenticated()
                && !CollectionUtils.containsAny(
                authentication.getAuthorities(), notUserAuthority)) {
            return Long.valueOf(authentication.getName());
        }
        // 스웨거 유저일시 유저 아이디 0 반환
        return 0L;
    }
}
