package org.survey.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.survey.model.user.Role;
import org.survey.model.user.User;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    FilterConfig filterConfig;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doFilterWithLoginUrl() throws ServletException, IOException {
        String contextPath = "/jwt-authentication";
        String loginUrl = "/api/rest/login";
        String requestUri=contextPath+loginUrl;
        prepareMock(loginUrl, contextPath, requestUri, null);
        doFilter();
        Mockito.verify(filterChain).doFilter(request, response);
    }
    @Test
    public void doFilterWithNoHeader() throws ServletException, IOException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        prepareMock(loginUrl, contextPath, requestUri, null);
        doFilter();
        Mockito.verify(response).sendError(401, "Unauthorized");
    }
    @Test
    public void doFilterWithValidToken() throws ServletException, IOException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        prepareMock(loginUrl, contextPath, requestUri, new JwtToken(new User("username", "password", "email", Role.ROLE_USER)).getToken());
        doFilter();
        Mockito.verify(filterChain).doFilter(request, response);
    }
    @Test
    public void doFilterWithBearerAndValidToken() throws ServletException, IOException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        prepareMock(loginUrl, contextPath, requestUri, "Bearer " + new JwtToken(new User("username", "password", "email", Role.ROLE_USER)).getToken());
        doFilter();
        Mockito.verify(filterChain).doFilter(request, response);
    }
    @Test
    public void doFilterWithExpiredToken() throws ServletException, IOException, InterruptedException {
        String loginUrl = "/api/rest/login";
        String contextPath = "/jwt-authentication";
        String requestUri="/jwt-authentication/api/rest/echo";
        User user = new User("username", "password", "email", Role.ROLE_USER);
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("username", user.getUsername());
        int expirySeconds=1;
        prepareMock(loginUrl, contextPath, requestUri, new JwtToken(payload, expirySeconds).getToken());
        Thread.sleep(1000);
        doFilter();
        Mockito.verify(response).sendError(401, "Unauthorized");
    }

    private void doFilter() throws ServletException, IOException {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.init(filterConfig);
        jwtAuthenticationFilter.doFilter(request, response, filterChain);
    }

    private void prepareMock(String loginUrl, String contextPath, String requestUri, String token) {
        Mockito.when(filterConfig.getInitParameter("loginUrl")).thenReturn(loginUrl);
        Mockito.when(request.getContextPath()).thenReturn(contextPath);
        Mockito.when(request.getRequestURI()).thenReturn(requestUri);
        Mockito.when(request.getHeader(JwtToken.AUTHORIZATION_HEADER)).thenReturn(token);
    }
}
