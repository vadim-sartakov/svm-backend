package svm.backend.data.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class TestConfiguration {
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        return new ReloadableResourceBundleMessageSource();
    }
}
