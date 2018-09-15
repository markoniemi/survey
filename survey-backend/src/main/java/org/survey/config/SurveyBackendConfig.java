package org.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.survey.DatabaseInitializer;

@Configuration
@Import({WebServiceConfig.class, RestConfig.class})
public class SurveyBackendConfig {
    @Bean
    public DatabaseInitializer getDatabaseInitializer() {
        return new DatabaseInitializer();
    }
}
