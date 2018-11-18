package org.survey;

import org.springframework.boot.SpringApplication;
import org.survey.config.ApplicationConfig;

// https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html
public class SurveyBackendApp {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
