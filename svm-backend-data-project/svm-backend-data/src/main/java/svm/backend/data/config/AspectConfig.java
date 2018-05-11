package svm.backend.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.security.FilterAspect;

@Configuration
public class AspectConfig {
    @Bean
    public FilterAspect filterAspect() {
        return new FilterAspect();
    }
}
