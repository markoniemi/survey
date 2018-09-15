package org.survey.config;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.survey.service.file.FileService;
import org.survey.service.login.LoginService;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;

import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:server.properties")
public class ServiceRestTestConfig {
// TODO add security
//	<http:conduit name="*.http-conduit">
//		<http:tlsClientParameters>
//			<sec:trustManagers>
//				<sec:keyStore type="JKS" password="changeit"
//    file="src/test/resources/survey.jks" />
//			</sec:trustManagers>
//		</http:tlsClientParameters>
//	</http:conduit>
    @Value("${http.protocol}://localhost:${http.port}/survey-backend/api/rest/")
    private String baseAddress;
    @Bean
    public List<?> getJsonProviders() {
        return Arrays.asList(new JacksonJaxbJsonProvider());
    }

    @Bean(name = "userService")
    public Object getUserService() {
        return JAXRSClientFactory.create(baseAddress, UserService.class, getJsonProviders());
    }

    @Bean(name = "fileService")
    public Object getFileService() {
        return JAXRSClientFactory.create(baseAddress, FileService.class, getJsonProviders());
    }

    @Bean(name = "pollService")
    public Object getPollService() {
        return JAXRSClientFactory.create(baseAddress, PollService.class, getJsonProviders());
    }
    @Bean(name = "loginService")
    public Object getLoginService() {
        return JAXRSClientFactory.create(baseAddress, LoginService.class, getJsonProviders());
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}