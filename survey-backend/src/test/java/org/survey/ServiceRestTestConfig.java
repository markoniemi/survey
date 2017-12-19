package org.survey;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.survey.service.file.FileService;
import org.survey.service.login.LoginService;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ServiceRestTestConfig {
    @Bean
    public List<?> getJsonProviders() {
        return Arrays.asList(new JacksonJaxbJsonProvider());
    }

    @Bean(name = "userService")
    public Object getUserService() {
        return JAXRSClientFactory.create("http://localhost:8082/survey-backend/api/rest", UserService.class, getJsonProviders());
    }

    @Bean(name = "fileService")
    public Object getFileService() {
        return JAXRSClientFactory.create("http://localhost:8082/survey-backend/api/rest", FileService.class, getJsonProviders());
    }

    @Bean(name = "pollService")
    public Object getPollService() {
        return JAXRSClientFactory.create("http://localhost:8082/survey-backend/api/rest", PollService.class, getJsonProviders());
    }
    @Bean(name = "loginService")
    public Object getLoginService() {
        return JAXRSClientFactory.create("http://localhost:8082/survey-backend/api/rest", LoginService.class, getJsonProviders());
    }
}