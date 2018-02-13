package svm.backend.signup.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.signup.service.PhonePasswordGenerator;
import svm.backend.signup.service.SimplePhonePasswordGenerator;

@Configuration
@ConditionalOnMissingBean(SMSAutoConfiguration.class)
public class SMSAutoConfiguration {
    
    @Bean
    public PhonePasswordGenerator phonePasswordGenerator() {
        return new SimplePhonePasswordGenerator();
    }
        
}
