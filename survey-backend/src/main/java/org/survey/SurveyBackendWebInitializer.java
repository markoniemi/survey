package org.survey;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.survey.security.JwtAuthenticationFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SurveyBackendWebInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext container) throws ServletException {
        createApplicationContext(container, SurveyBackendConfig.class);
        // TODO enable security
//        registerSecurityFilter(container);
        registerCxfServlet(container);
//        registerJwtAuthenticationFilter(container);
    }

    private WebApplicationContext createApplicationContext(ServletContext container, Class configClass) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(configClass);
        applicationContext.setServletContext(container);
        container.addListener(new ContextLoaderListener(applicationContext));
        return applicationContext;
    }

    private void registerJwtAuthenticationFilter(ServletContext container) {
        // TODO change JwtAuthenticationFilter to accept constructor parameters
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        container.addFilter("jwtAuthenticationFilter", jwtAuthenticationFilter).addMappingForUrlPatterns(null, false, "/api/rest/*");
    }

    private void registerCxfServlet(ServletContext container) {
        ServletRegistration.Dynamic dispatcher
                = container.addServlet("dispatcher", new CXFServlet());
        dispatcher.addMapping("/api/*");
    }

    private void registerSecurityFilter(ServletContext container) {
        container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");
    }
}
