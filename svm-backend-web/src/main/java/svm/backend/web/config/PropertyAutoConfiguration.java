package svm.backend.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:properties/web/application.properties")
@EnableConfigurationProperties(WebProperties.class)
public class PropertyAutoConfiguration {
        
}
