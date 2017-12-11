package org.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.survey.model.user.Role;
import org.survey.security.UserRepositoryAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Bean
//    public AuthenticationProvider getAuthenticationProvider() {
//        return new UserRepositoryAuthenticationProvider();
//    }

    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
//        auth.authenticationProvider(getAuthenticationProvider());
        auth.inMemoryAuthentication().withUser("admin")
                .password("admin").roles(Role.ROLE_ADMIN.name());
//                .and().withUser("user").password("user").roles(Role.ROLE_ADMIN.name());
    }

    // http://www.baeldung.com/java-config-spring-security
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/pages/**")
                .hasAnyRole(Role.ROLE_USER.name(), Role.ROLE_ADMIN.name());
        http.authorizeRequests().antMatchers("/pages/admin/**")
                .hasAnyRole(Role.ROLE_ADMIN.name())
                .and().formLogin()
                .loginPage("/login.xhtml")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .failureUrl("/login.xhtml?loginError=true")
                .successForwardUrl("/pages/users.xhtml");
        http.logout()
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/pages/users.xhtml");
    }
}