package svm.backend.security.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.security.service.UsernameUserDetailsService;

@Configuration
@PropertySource("classpath:security.properties")
public class CoreAutoConfiguration implements InitializingBean {
    
    @Autowired private ReloadableResourceBundleMessageSource messageSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.addBasenames(
                "classpath:locale/security_messages",
                "classpath:org/springframework/security/messages"
        );
    }
    
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new UsernameUserDetailsService();
    }
        
}
