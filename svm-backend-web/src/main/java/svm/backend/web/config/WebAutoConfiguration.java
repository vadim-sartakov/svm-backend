package svm.backend.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnMissingBean(WebAutoConfiguration.class)
@ComponentScan("svm.backend.web.controller")
@PropertySource("classpath:properties/web/application.properties")
@EnableConfigurationProperties(WebProperties.class)
@Import({ RepositoryRestBaseConfiguration.class, WebMvcConfiguration.class, MessagesConfiguration.class })
public class WebAutoConfiguration {
    
}
