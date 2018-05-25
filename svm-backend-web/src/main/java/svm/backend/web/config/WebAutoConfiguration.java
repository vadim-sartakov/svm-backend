package svm.backend.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import svm.backend.core.service.ExceptionFactory;
import svm.backend.core.service.MessagesBasenameProvider;
import svm.backend.web.exception.DefaultExceptionFactory;

@Configuration
@ConditionalOnMissingBean(WebAutoConfiguration.class)
@ComponentScan("svm.backend.web.exception")
@PropertySource("classpath:properties/web/application.properties")
@EnableConfigurationProperties(WebProperties.class)
@Import({ RepositoryRestBaseConfiguration.class, WebMvcConfiguration.class })
public class WebAutoConfiguration {
    
    @Bean
    public MessagesBasenameProvider webMessagesBasenames() {
        return () -> "classpath:locale/web/messages";
    }
    
    @Bean
    @ConditionalOnMissingBean(ExceptionFactory.class)
    public ExceptionFactory defaultExceptionFactory() {
        return new DefaultExceptionFactory();
    }
    
}
