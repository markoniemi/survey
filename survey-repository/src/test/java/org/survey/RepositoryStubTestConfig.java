package org.survey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.survey.repository.file.FileRepository;
import org.survey.repository.file.FileRepositoryStub;
import org.survey.repository.poll.PollRepository;
import org.survey.repository.poll.PollRepositoryStub;
import org.survey.repository.user.UserRepository;
import org.survey.repository.user.UserRepositoryStub;

@Configuration
@ComponentScan(basePackages = "org.survey")
public class RepositoryStubTestConfig {
    @Bean
    public UserRepository getUserRepository() {
        return new UserRepositoryStub();
    }

    @Bean
    public FileRepository getFileRepository() {
        return new FileRepositoryStub();
    }

    @Bean
    public PollRepository getPollRepository() {
        return new PollRepositoryStub();
    }
}
