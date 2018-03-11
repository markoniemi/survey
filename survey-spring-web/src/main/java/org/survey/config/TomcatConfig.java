package org.survey.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
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

    @Bean
    public String contextPath() {
        return "/survey-spring-web";
    }

    @Bean
    @DependsOn("contextPath")
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        factory.setContextPath(contextPath());
        // https://stackoverflow.com/questions/43264890/after-upgrade-from-spring-boot-1-2-to-1-5-2-filenotfoundexception-during-tomcat
        String[] tldSkipPatterns = { "jaxb-core.jar", "jaxb-api.jar", "xml-apis.jar", "xercesImpl.jar", "xml-apis.jar",
                "serializer.jar" };
        factory.setTldSkipPatterns(Arrays.asList(tldSkipPatterns));
        return factory;
    }

    // TODO move to some test config?
    // TODO read contextPath from config
    @Bean
    @DependsOn("servletContainer")
    public String url() {
        return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port).path(contextPath())
                .build().toString();
    }
}
