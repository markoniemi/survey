package org.survey.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SurveySpringWebInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) throws ServletException {
        registerDispatcherServlet(container, createApplicationContext(container, WebMvcConfig.class));
        registerSecurityFilter(container);
    }

    private void registerSecurityFilter(ServletContext container) {
        container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");
    }

    private void registerDispatcherServlet(ServletContext container, WebApplicationContext applicationContext) {
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher",
                new DispatcherServlet(applicationContext));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }

    private WebApplicationContext createApplicationContext(ServletContext container, Class configClass) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(configClass);
        applicationContext.setServletContext(container);
        container.addListener(new ContextLoaderListener(applicationContext));
        return applicationContext;
    }
}
