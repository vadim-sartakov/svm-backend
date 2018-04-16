package svm.backend.web.service;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public interface CorsConfigurer {
    void addCorsMappings(CorsRegistry registry);
}
