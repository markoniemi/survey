package org.survey;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.survey.service.file.FileService;
import org.survey.service.login.LoginService;
import org.survey.service.login.LoginServiceImpl;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;
import org.survey.service.user.UserServiceImpl;

import javax.annotation.Resource;

//https://www.programcreek.com/java-api-examples/index.php?class=org.apache.cxf.jaxrs.JAXRSServerFactoryBean&method=setProvider
//http://www.baeldung.com/apache-cxf-rest-api
@Configuration
//@RestControllerAdvice(basePackages="org.survey")
@Import({ServiceConfig.class})
public class RestConfig {
    @Resource(name="userServiceBean")
    private UserService userService;
    @Resource(name="fileServiceBean")
    private FileService fileService;
    @Resource(name="pollServiceBean")
    private PollService pollService;
    @Bean("loginServiceBean")
    public LoginService getLoginService(){
        return new LoginServiceImpl();
    }

    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean(destroyMethod = "destroy")
    @DependsOn("cxf")
    public Server jaxRsServer() {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setServiceBeanObjects(userService,fileService,pollService,getLoginService());
        factory.setProvider(new JacksonJsonProvider());
        factory.setBus(cxf());
        factory.setAddress("/rest");
        return factory.create();
    }
//    @Bean
//    public RepositoryRestConfigurer repositoryRestConfigurer() {
//        return new RepositoryRestConfigurerAdapter() {
//            @Override
//            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//                config.setDefaultMediaType(MediaType.APPLICATION_JSON);
//                config.useHalAsDefaultJsonMediaType(false);
//                config.getMetadataConfiguration().setAlpsEnabled(false);
//            }
//        };
//    }

//    @Bean
//    public ServletRegistrationBean cxfServlet() {
//        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(), "/api/*");
//        servletRegistrationBean.setLoadOnStartup(1);
//        return servletRegistrationBean;
//    }
}
