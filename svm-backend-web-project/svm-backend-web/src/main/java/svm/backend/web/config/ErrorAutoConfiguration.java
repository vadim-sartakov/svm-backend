package svm.backend.web.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.web.exception.CustomErrorAttributes;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ErrorAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(CustomErrorAttributes.class)
    public CustomErrorAttributes customErrorAttributes() {
        return new CustomErrorAttributes();
    }
    
}
