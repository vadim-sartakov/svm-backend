package svm.backend.web.config;

import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.web.exception.CustomErrorAttributes;
import svm.backend.web.exception.handler.ExceptionHandler;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ErrorAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(CustomErrorAttributes.class)
    public CustomErrorAttributes customErrorAttributes(List<ExceptionHandler<?>> handlers) {
        return new CustomErrorAttributes(handlers);
    }
    
}
