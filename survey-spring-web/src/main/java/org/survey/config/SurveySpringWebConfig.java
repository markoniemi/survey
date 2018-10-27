package org.survey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ServiceConfig.class,
        WebSecurityConfig.class,
        WebMvcConfig.class
})
public class SurveySpringWebConfig {
}
