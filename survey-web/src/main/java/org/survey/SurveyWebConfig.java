package org.survey;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
//        WebServiceConfig.class,
//        RestConfig.class,
        ServiceConfig.class,
        SecurityConfig.class})
public class SurveyWebConfig {
}
