package svm.backend.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.security.datachange.UserPredefined;

@Configuration
public class DataChangeAutoConfigration {
        
    @Bean
    @ConditionalOnMissingBean
    public UserPredefined userPredefined() {
        return new UserPredefined();
    }
        
}
