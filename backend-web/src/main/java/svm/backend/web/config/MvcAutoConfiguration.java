package svm.backend.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcAutoConfiguration extends WebMvcConfigurerAdapter {
        
    @Autowired private MessageSource messageSource;
    @Autowired private LocalValidatorFactoryBean validator;
    
    public static void addLocalhostCors(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("*")
                .exposedHeaders("Location")
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
                
        
    @Override
    public Validator getValidator() {
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
      
}
