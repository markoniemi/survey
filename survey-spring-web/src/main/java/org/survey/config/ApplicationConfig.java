package org.survey.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "org.survey")
@EntityScan(basePackages = "org.survey.model")
@Import({ JpaConfig.class, TomcatConfig.class, WebMvcConfig.class, WebSecurityConfig.class })
public class ApplicationConfig {
    @Bean
    public DatabaseInitializer getDatabaseInitializer() {
        return new DatabaseInitializer();
    }
}