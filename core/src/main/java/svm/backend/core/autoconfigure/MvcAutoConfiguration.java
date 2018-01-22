package svm.backend.core.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import svm.backend.security.JWTCsrfTokenRepository;

@Configuration
public class MvcAutoConfiguration extends WebMvcConfigurerAdapter {
        
    public static void addLocalhostCors(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("*")
                .exposedHeaders("Location", JWTCsrfTokenRepository.DEFAULT_CSRF_HEADER_NAME)
                .allowedMethods("*");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        addLocalhostCors(registry);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/{basePath:^(?!api|static).*$}/**").setViewName("index");
    }
      
}
