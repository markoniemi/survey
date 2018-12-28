package org.survey.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import io.github.bonigarcia.wdm.ChromeDriverManager;

@ComponentScan(basePackages = "org.selenium")
@PropertySource("classpath:server.properties")
public class SeleniumConfig {
    @Value("${http.port}")
    private int port;
    @Value("${http.protocol}")
    private String protocol;

    @Bean(name = "loginUrl")
    public String getLoginUrl() {
        return protocol + "://localhost:" + port + contextPath();
    }

    @Bean
    public WebDriver getWebDriver() {
        ChromeDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x800");
        options.addArguments("no-sandbox");
        options.addArguments("proxy-server='direct://'");
        options.addArguments("proxy-bypass-list=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        return chromeDriver;
    }

    @Bean
    public String contextPath() {
        return "/survey-spring-web";
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
