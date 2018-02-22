package svm.backend.security.config;

import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.security.service.UsernameUserDetailsService;

@Configuration
public class SecurityAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new UsernameUserDetailsService();
    }
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.ENGLISH);
        messageSource.addBasenames(
                "classpath:locale/messages",
                "classpath:locale/security_messages",
                "classpath:org/springframework/security/messages"
        );
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
    
}
