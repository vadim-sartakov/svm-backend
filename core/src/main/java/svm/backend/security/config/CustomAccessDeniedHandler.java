package svm.backend.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import svm.backend.controller.exception.ApiException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendError(response);
    }
    
    private void sendError(HttpServletResponse response) {
        
        ApiException exception = new ApiException(HttpStatus.FORBIDDEN, "Access denied");
        
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON);
                
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(exception);
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
