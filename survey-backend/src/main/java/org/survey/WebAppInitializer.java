//TODO
//http://www.baeldung.com/apache-cxf-with-spring
//package org.survey;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletRegistration;
//
//public class WebAppInitializer implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext container) {
//        AnnotationConfigWebApplicationContext context
//                = new AnnotationConfigWebApplicationContext();
//        context.setConfigLocation("com.example.app.config");
//
//        container.addListener(new ContextLoaderListener(context));
//
//        ServletRegistration.Dynamic dispatcher = container
//                .addServlet("dispatcher", new DispatcherServlet(context));
//
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//    }
//}