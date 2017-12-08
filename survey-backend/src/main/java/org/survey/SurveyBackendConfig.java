package org.survey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebServiceConfig.class, RestConfig.class})
public class SurveyBackendConfig {
    @Bean
    public DatabaseInitializer getDatabaseInitializer() {
        return new DatabaseInitializer();
    }
}
