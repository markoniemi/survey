package org.survey.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWTVerifyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter implements Filter {
    protected String loginUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        loginUrl = filterConfig.getInitParameter("loginUrl");
        log.debug("Using loginUrl: {}", loginUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (isLoginUrl(request)) {
            log.trace("URL {} is login URL.", request.getRequestURI());
            filterChain.doFilter(request, response);
        } else {
            JwtToken token = getToken(request);
            if (token != null) {
                try {
                    token.verifyToken();
                    log.debug("URL {} is authenticated", request.getRequestURI());
                    filterChain.doFilter(request, response);
                    // TODO show different message for different errors
                } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException
                        | JWTVerifyException e) {
                    log.trace(String.format("URL %s is not authenticated", request.getRequestURI()), e);
                    sendError(response);
                }
            } else {
                log.trace("URL {} is not authenticated", request.getRequestURI());
                sendError(response);
            }
        }
    }

    /**
     * Token is in request header: Authorization : Bearer <token>. Accepts
     * header with Bearer prefix and without it.
     * 
     * @return null if token was not found.
     */
    protected JwtToken getToken(HttpServletRequest request) {
        return JwtToken.parseToken(request.getHeader(JwtToken.AUTHORIZATION_HEADER));
    }

    private boolean isLoginUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        return uri.equals(contextPath + loginUrl);
    }

    @Override
    public void destroy() {
        // no action
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setHeader("WWW-Authenticate", "JWT");
        response.sendError(401, message);
    }

    private void sendError(HttpServletResponse response) throws IOException {
        sendError(response, "Unauthorized");
    }
}