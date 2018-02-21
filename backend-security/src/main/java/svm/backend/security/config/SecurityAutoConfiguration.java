package svm.backend.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.security.service.UsernameUserDetailsService;

@Configuration
public class SecurityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new UsernameUserDetailsService();
    }
}
