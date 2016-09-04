package org.survey.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    /** Util class has a private constructor */
    private SecurityUtil() {
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            return grantedAuthority.getAuthority();
        }
        return null;
    }
}
