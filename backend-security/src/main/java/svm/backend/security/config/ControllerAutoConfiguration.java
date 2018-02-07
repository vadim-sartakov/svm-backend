package svm.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.security.exception.RestExceptionHandler;

@Configuration
public class ControllerAutoConfiguration {
    
    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
    
}
