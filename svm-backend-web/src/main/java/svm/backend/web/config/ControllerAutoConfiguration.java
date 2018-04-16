package svm.backend.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.web.controller.WebExceptionHandler;

@Configuration
public class ControllerAutoConfiguration {
    @Bean
    public WebExceptionHandler restExceptionHandler() {
        return new WebExceptionHandler();
    }
}
