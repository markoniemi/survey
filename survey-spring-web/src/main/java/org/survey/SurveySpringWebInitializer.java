package org.survey;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SurveySpringWebInitializer implements WebApplicationInitializer {

        public void onStartup(ServletContext container) throws ServletException {

            AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
            ctx.register(MvcConfig.class);
            ctx.setServletContext(container);

            ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));

            servlet.setLoadOnStartup(1);
            servlet.addMapping("/");
        }
}
