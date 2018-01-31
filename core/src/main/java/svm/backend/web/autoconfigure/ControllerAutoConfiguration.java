package svm.backend.web.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.web.exception.RestExceptionHandler;

@Configuration
public class ControllerAutoConfiguration {
    
    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
    
}
