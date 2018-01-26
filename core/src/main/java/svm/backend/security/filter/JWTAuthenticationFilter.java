package svm.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import svm.backend.security.JWTService;
import svm.backend.security.JWTCsrfTokenRepository;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        
    @Autowired private JWTService jwtService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        Credentials credentials;
        try {
            credentials = objectMapper.readValue(request.getInputStream(), Credentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getLogin(),
                        credentials.getPassword(),
                        new ArrayList<>()
                )
        );
        
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String csrfToken = UUID.randomUUID().toString();
        String jwt = jwtService.generateToken(authResult, csrfToken);

        addCookie(request, response, JWTService.COOKIE_NAME, jwt, true, 7200);
        addCookie(request, response, JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME, csrfToken, false, 0);
        
    }
    
    private void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, boolean httpOnly, int maxAge) {
        
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge != 0)
            cookie.setMaxAge(maxAge);
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
        
    }
    
    @Data
    public static class Credentials {
        private String login;
        private String password;
    }
    
}
