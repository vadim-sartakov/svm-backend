package svm.backend.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Import({ AuthorizationServerConfiguration.class, MessagesConfiguration.class })
public class SecurityAutoConfiguration {
        
}
