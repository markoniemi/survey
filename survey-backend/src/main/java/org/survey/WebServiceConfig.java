package org.survey;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.survey.service.file.FileService;
import org.survey.service.poll.PollService;
import org.survey.service.user.UserService;
import org.survey.service.user.UserServiceImpl;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

@Configuration
@Import({ServiceConfig.class})
public class WebServiceConfig {
    @Resource(name="userServiceBean")
    private UserService userService;
    @Resource(name="fileServiceBean")
    private FileService fileService;
    @Resource(name="pollServiceBean")
    private PollService pollService;
    @Resource
    private Bus bus;

    @Bean
    public Endpoint userServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, userService);
        endpoint.setAddress("/soap/userService");
        endpoint.publish("/soap/userService");
        return endpoint;
    }

    @Bean
    public Endpoint fileServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, fileService);
        endpoint.setAddress("/soap/fileService");
        endpoint.publish("/soap/fileService");
        return endpoint;
    }

    @Bean
    public Endpoint pollServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, pollService);
        endpoint.setAddress("/soap/pollService");
        endpoint.publish("/soap/pollService");
        return endpoint;
    }
}