package com.mdd.ela.util;

import com.mdd.ela.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

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
            var user = (Account) SecurityContextHolder.getContext().getAuthentication();
            return user.getId();

        } catch (ClassCastException | NumberFormatException ex) {
            return 0L;
        }
    }
}
