package svm.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.security.datachange.UserPredefined;

@Configuration
public class DataChangeAutoConfigration {
        
    @Bean
    public UserPredefined userPredefined() {
        return new UserPredefined();
    }
        
}
