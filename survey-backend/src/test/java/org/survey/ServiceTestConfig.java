package org.survey;

import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.survey.service.file.FileService;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

import java.net.URL;

@Configuration
public class ServiceTestConfig {
    @Bean(name = "userService")
    public Object getUserService() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(UserService.class);
        proxyFactory.setAddress("http://localhost:8082/survey-backend/api/soap/userService");
        return proxyFactory.create();
    }
    @Bean(name = "fileService")
    public Object getFileService() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(FileService.class);
        proxyFactory.setAddress("http://localhost:8082/survey-backend/api/soap/fileService");
        return proxyFactory.create();
    }
    @Bean(name = "pollService")
    public Object getPollService() {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(PollService.class);
        proxyFactory.setAddress("http://localhost:8082/survey-backend/api/soap/pollService");
        return proxyFactory.create();
    }

//    @Bean
//    public JaxWsProxyFactoryBean proxyFactoryBean() {
//        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
//        proxyFactory.setServiceClass(UserService.class);
//        proxyFactory.setAddress("http://localhost:8082/survey-backend/api/soap/userService");
//        return proxyFactory;
//    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer ret = new PropertySourcesPlaceholderConfigurer();
//        ret.setLocation(new ClassPathResource("application.properties"));
        ret.setLocation(new ClassPathResource("server.properties"));
        return ret;
    }
}
