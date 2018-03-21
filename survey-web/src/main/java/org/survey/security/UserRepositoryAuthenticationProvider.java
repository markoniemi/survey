package org.survey.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring security AuthenticationProvider which authenticates using
 * UserRepository.
 * 
 * @see http://tedyoung.me/2011/06/21/spring-security-custom-authenticators/
 * @see http
 *      ://samerabdelkafi.wordpress.com/2011/01/16/secure-your-web-application
 *      -with-spring-security/
 */
@Slf4j
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {
    @Resource(name="userServiceBean")
    UserService userService;

    /**
     * Authenticate using UserRepository.
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        log.debug("authenticate:" + authentication.getName());
        User user = userService.findOne(authentication.getName());
        return UserRepositoryAuthenticationProvider.authenticateUser(user, authentication);
    }

    private static Authentication authenticateUser(User user, Authentication authentication) {
        if (user != null && authentication.getCredentials().equals(user.getPassword())) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),
                    authorities);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
