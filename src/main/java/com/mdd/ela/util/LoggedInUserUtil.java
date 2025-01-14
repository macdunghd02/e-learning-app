package com.mdd.ela.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;

public class LoggedInUserUtil {
    private LoggedInUserUtil() {

    }

    public static User getLoggedInUser() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException ex) {
            return new User("1", "admin", Collections.emptyList());
        }
    }

    public static long getIdOfLoggedInUser() {
        try {
            // Lấy Authentication từ SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
                Jwt jwt = (Jwt) jwtAuthenticationToken.getCredentials();

                // Truy xuất thông tin id từ claims của JWT
                return Long.parseLong(jwt.getClaimAsString("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Log lỗi
        }
        return 0L; // Trả về 0 nếu có lỗi
    }
    }
