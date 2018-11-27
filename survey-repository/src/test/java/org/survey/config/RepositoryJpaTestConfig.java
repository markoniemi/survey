package org.survey.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.survey")
@Import({ JpaConfig.class })
public class RepositoryJpaTestConfig {
}
