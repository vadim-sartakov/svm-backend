package svm.backend.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import svm.backend.web.controller.ApiException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MessageSource messageSource;
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendError(response);
    }
    
    private void sendError(HttpServletResponse response) {
        
        String message = messageSource.getMessage("AbstractAccessDecisionManager.accessDenied", null, LocaleContextHolder.getLocale());
        ApiException exception = new ApiException(HttpStatus.FORBIDDEN,  message);
        
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
                
        try {
            String jsonResponse = objectMapper.writeValueAsString(exception);
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        
    }
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        sendError(response);
    }
    
}
