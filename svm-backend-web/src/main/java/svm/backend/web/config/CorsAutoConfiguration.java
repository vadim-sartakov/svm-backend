package svm.backend.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.web.service.CorsConfigurer;
import svm.backend.web.service.DefaultCorsConfigurer;

@Configuration
@ConditionalOnMissingBean(CorsConfigurer.class)
public class CorsAutoConfiguration {
    @Bean
    public CorsConfigurer corsConfigurer() {
        return new DefaultCorsConfigurer();
    }
}
