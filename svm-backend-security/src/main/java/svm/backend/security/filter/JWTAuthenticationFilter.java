package svm.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import svm.backend.security.dao.entity.User;
import svm.backend.security.service.JWTService;
import svm.backend.security.service.JWTCsrfTokenRepository;
import svm.backend.web.controller.ApiException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        
    private final AuthenticationManager authenticationManager;
    
    @Autowired private EntityLinks entityLinks;
    @Autowired private MessageSource messageSource;
    @Autowired private JWTService jwtService;
    @Autowired private ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @PostConstruct
    public void initialize() {
        this.setAuthenticationManager(authenticationManager);
    }
    
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
                        credentials.getUsername(),
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

        User authenticated = (User) authResult.getPrincipal();
        String userLink = entityLinks
                .linkToSingleResource(User.class, authenticated.getId())
                .getHref();
        response.addHeader(HttpHeaders.LOCATION, userLink);
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        String message = messageSource.getMessage("svm.backend.security.BadCredentials", null, LocaleContextHolder.getLocale());
        ApiException exception = new ApiException(HttpStatus.UNAUTHORIZED,  message);
        
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                
        try {
            String jsonResponse = objectMapper.writeValueAsString(exception);
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        
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
        private String username;
        private String password;
    }
    
}
