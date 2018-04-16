package svm.backend.web.service;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class DefaultCorsConfigurer implements CorsConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("*")
                .exposedHeaders("Location")
                .allowedMethods("*");
    }
}
