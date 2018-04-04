package svm.backend.signup.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.signup.service.EmailPasswordGenerator;
import svm.backend.signup.service.SimpleEmailPasswordGenerator;

@Configuration
@ConditionalOnMissingBean(EmailAutoConfiguration.class)
public class EmailAutoConfiguration {
    
    @Bean
    public EmailPasswordGenerator emailPasswordGenerator() {
        return new SimpleEmailPasswordGenerator();
    }

}
