package svm.backend.controller.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.controller.exception.RestExceptionHandler;

@Configuration
public class ControllerAutoConfig {
    
    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
    
}
