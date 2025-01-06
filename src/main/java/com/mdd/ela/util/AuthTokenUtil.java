package com.mdd.ela.util;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TruongVd
 * @date 06/10/2023
 * @project Ela-backend
 */
public class AuthTokenUtil {
    private AuthTokenUtil() {
    }
    public static Map<String,Object> getTokenInfo(){
        Map<String, Object> resultMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> arrayList = authentication.getAuthorities();
        for (GrantedAuthority item : arrayList) {
            String[] keyValue = item.toString().split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                Object parsedValue;
                try {
                    parsedValue = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    parsedValue = value;
                }

                resultMap.put(key, parsedValue);
            }
        }
        return resultMap;
    }
}
