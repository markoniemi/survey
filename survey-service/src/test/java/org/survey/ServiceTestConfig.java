package org.survey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.survey.service.file.FileService;
import org.survey.service.file.FileServiceImpl;
import org.survey.service.poll.PollService;
import org.survey.service.poll.PollServiceImpl;
import org.survey.service.user.UserService;
import org.survey.service.user.UserServiceImpl;

@Configuration
//@ComponentScan(basePackages = "org.survey")
@Import({ JpaConfig.class})
public class ServiceTestConfig {
    @Bean("userService")
    public UserService getUserService(){
        return new UserServiceImpl();
    }
    @Bean("pollService")
    public PollService getPollService(){
        return new PollServiceImpl();
    }
    @Bean("fileService")
    public FileService getFileService(){
        return new FileServiceImpl();
    }
}
