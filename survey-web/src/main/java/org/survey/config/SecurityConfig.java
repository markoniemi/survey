package org.survey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.survey.security.UserRepositoryAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return new UserRepositoryAuthenticationProvider();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) {
        web.debug(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/pages/**").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/pages/admin/**").hasAnyRole("ADMIN");
        http.authorizeRequests().and().formLogin().loginPage("/login.xhtml")
                .loginProcessingUrl("/j_spring_security_check").usernameParameter("j_username")
                .passwordParameter("j_password").failureUrl("/login.xhtml?loginError=true")
                .defaultSuccessUrl("/pages/users.xhtml");
        http.logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/pages/users.xhtml");
    }
}