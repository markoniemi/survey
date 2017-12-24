package org.survey;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.survey.service.file.FileService;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

@Configuration
@PropertySource("classpath:server.properties")
public class ServiceTestConfig {
    @Value("${http.protocol}://localhost:${http.port}/survey-backend/api/soap/")
    private String baseAddress;

    @Bean(name = "userService")
    public Object getUserService() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(UserService.class);
        proxyFactory.setAddress(baseAddress + "userService");
        return proxyFactory.create();
    }

    @Bean(name = "fileService")
    public Object getFileService() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(FileService.class);
        proxyFactory.setAddress(baseAddress + "fileService");
        return proxyFactory.create();
    }

    @Bean(name = "pollService")
    public Object getPollService() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(PollService.class);
        proxyFactory.setAddress(baseAddress + "pollService");
        return proxyFactory.create();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
