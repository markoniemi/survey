package org.survey.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@PropertySource("classpath:server.properties")
public class TomcatConfig {
    @Value("${http.port}")
    private int port;
    @Value("${context.path}")
    private String contextPath;

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(port);
        factory.setContextPath(contextPath);
        // https://stackoverflow.com/questions/43264890/after-upgrade-from-spring-boot-1-2-to-1-5-2-filenotfoundexception-during-tomcat
        String[] tldSkipPatterns = { "jaxb-core.jar", "jaxb-api.jar", "xml-apis.jar", "xercesImpl.jar", "xml-apis.jar",
                "serializer.jar" };
        factory.setTldSkipPatterns(Arrays.asList(tldSkipPatterns));
        return factory;
    }

    @Bean
    @DependsOn("servletContainer")
    public String url() {
        return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port).path(contextPath)
                .build().toString();
    }
}
