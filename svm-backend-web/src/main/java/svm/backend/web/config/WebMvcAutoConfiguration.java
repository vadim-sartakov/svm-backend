package svm.backend.web.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import svm.backend.web.service.CorsConfigurer;

@Configuration
public class WebMvcAutoConfiguration extends WebMvcConfigurerAdapter {
        
    @Autowired private MessageSource messageSource;
    @Autowired private LocalValidatorFactoryBean validator;
    @Autowired private Optional<CorsConfigurer> corsConfigurer;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        corsConfigurer.ifPresent(configurer -> configurer.addCorsMappings(registry));
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/{basePath:^(?!api|static).*$}/**").setViewName("index");
    }
                
        
    @Override
    public Validator getValidator() {
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
      
}
