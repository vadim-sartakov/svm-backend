package svm.backend.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import svm.backend.security.exception.ApiErrorWebResponseExceptionTranslator;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@PropertySource("classpath:properties/security/application.properties")
//@ComponentScan("svm.backend.security.exception")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Import({ AuthorizationServerConfiguration.class, MessagesConfiguration.class })
public class SecurityAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new ApiErrorWebResponseExceptionTranslator();
    }
    
}
