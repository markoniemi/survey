package org.survey;

import org.springframework.boot.SpringApplication;
import org.survey.config.ApplicationConfig;
// https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html
//@SpringBootApplication
//@Configuration 
//@EnableAutoConfiguration 
//@ComponentScan
//@EnableWebMvc
public class SurveySpringWebApp  {
//public class SurveySpringWebApp extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
//        SpringApplication.run(SurveySpringWebApp.class, args);
    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
//        return applicationBuilder.sources(SurveySpringWebApp.class);
//    }    
}
