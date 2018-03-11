package org.survey;

import org.springframework.boot.SpringApplication;
import org.survey.config.ApplicationConfig;

//@SpringBootApplication
//@Configuration 
//@EnableAutoConfiguration 
//@ComponentScan
//@EnableWebMvc
public class SurveySpringWebApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SampleWebJspApplication.class);
//    }    
}
