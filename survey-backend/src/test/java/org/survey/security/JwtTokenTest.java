package org.survey.security;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.survey.model.user.Role;
import org.survey.model.user.User;

import com.auth0.jwt.JWTExpiredException;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifyException;

public class JwtTokenTest {
    @Test
    public void parseToken() {
        String header = "Bearer <token>";
        Assert.assertEquals("<token>", JwtToken.parseToken(header).getToken());
    }

    @Test
    public void parseTokenWithoutPrefix() {
        String header = "<token>";
        Assert.assertEquals("<token>", JwtToken.parseToken(header).getToken());
    }

    @Test
    public void verifyToken() {
        try {
            User user = new User("username", "password", "email", Role.ROLE_USER);
            Map<String, Object> payload = new HashMap<String, Object>();
            payload.put("username", user.getUsername());
            JwtToken token = new JwtToken(payload, 10);
            token.verifyToken();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void verifyTokenWithExpiredToken() {
        try {
            User user = new User("username", "password", "email", Role.ROLE_USER);
            Map<String, Object> payload = new HashMap<String, Object>();
            payload.put("username", user.getUsername());
            JwtToken token = new JwtToken(payload, 1);
            Thread.sleep(1000);
            token.verifyToken();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof JWTExpiredException);
        }
    }

    @Test()
    public void verifyTokenWithInvalidSignature() {
        try {
            User user = new User("username", "password", "email", Role.ROLE_USER);
            JWTSigner jwtSigner = new JWTSigner("wrong_secret");
            Map<String, Object> payload = new HashMap<String, Object>();
            payload.put("username", user.getUsername());
            String tokenString = jwtSigner.sign(payload);
            JwtToken token = new JwtToken(tokenString);
            token.verifyToken();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof JWTVerifyException);
        }
    }
}
