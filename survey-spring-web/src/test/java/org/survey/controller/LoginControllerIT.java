package org.survey.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.survey.SurveySpringWebApp;
import org.survey.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveySpringWebApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
public class LoginControllerIT {
    @Resource
    private String url;

    @Test
    public void login() throws Exception {
        HttpHeaders headers = createHeaders();
        MultiValueMap<String, String> body = createBody("admin", "admin");
        ResponseEntity<String> entity = new TestRestTemplate().exchange(url + "/j_spring_security_check",
                HttpMethod.POST, new HttpEntity<>(body,headers), String.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<String> cookies = entity.getHeaders().get("Set-Cookie");
        Assert.assertTrue(cookies.toString().contains("JSESSIONID"));
        Assert.assertTrue(entity.getBody().contains("<td id=\"username\">admin</td>"));
        Assert.assertTrue(entity.getBody().contains("<td id=\"role\">Admin</td>"));
        
    }

    @Test
    public void redirectToLogin() throws Exception {
        HttpHeaders headers = createHeaders();
        ResponseEntity<String> entity = new TestRestTemplate().exchange(url + "/user/users", HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        Assert.assertEquals(HttpStatus.FOUND, entity.getStatusCode());
        Assert.assertEquals(url + "/login", entity.getHeaders().getLocation().toString());
    }
    @Test
    public void failedLogin() throws Exception {
        HttpHeaders headers = createHeaders();
        MultiValueMap<String, String> body = createBody("admin", "wrong");
        ResponseEntity<String> entity = new TestRestTemplate().exchange(url + "/j_spring_security_check",
                HttpMethod.POST, new HttpEntity<>(body,headers), String.class);
        Assert.assertEquals(HttpStatus.FOUND, entity.getStatusCode());
        Assert.assertEquals(url + "/login?error=true", entity.getHeaders().getLocation().toString());
    }

    private MultiValueMap<String, String> createBody(String username, String password) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("j_username", username);
        form.set("j_password", password);
        return form;
    }
    
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
