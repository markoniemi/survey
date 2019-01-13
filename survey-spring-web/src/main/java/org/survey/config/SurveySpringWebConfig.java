package org.survey.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.log4j.Log4j2;

@Configuration
@Import({ ServiceConfig.class, WebSecurityConfig.class, WebMvcConfig.class })
// read values from classpath or file, or override them from environment variables
@PropertySource(ignoreResourceNotFound = true, value = {"file:target/classes/test.properties","classpath:default.properties"})
@Log4j2
public class SurveySpringWebConfig {
    @Value("${test.value}")
    private String value;

    @PostConstruct
    public void init() {
        log.trace(value);
    }
}
