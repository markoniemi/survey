package org.survey.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class UserBean {
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            return grantedAuthority.getAuthority();
        }
        return null;
    }
}
