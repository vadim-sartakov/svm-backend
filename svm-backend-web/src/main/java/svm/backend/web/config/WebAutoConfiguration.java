package svm.backend.web.config;

import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ConditionalOnMissingBean(WebAutoConfiguration.class)
@ComponentScan("svm.backend.web.controller")
@PropertySource("classpath:properties/web/application.properties")
@EnableConfigurationProperties(WebProperties.class)
@Import({ RepositoryRestBaseConfiguration.class, WebMvcConfiguration.class })
public class WebAutoConfiguration {
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.US);
        messageSource.addBasenames("classpath:locale/web/messages");
        return messageSource;
    }
    
}
