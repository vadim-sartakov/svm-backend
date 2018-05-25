package svm.backend.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ConditionalOnMissingBean(WebMvcConfiguration.class)
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
        
    @Autowired private MessageSource messageSource;
    @Autowired private LocalValidatorFactoryBean validator;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        webMvcConfigurer().addCorsMappings(registry);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        webMvcConfigurer().addViewControllers(registry);
    }
                
    @Override
    public Validator getValidator() {
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
    
    @Bean
    @ConditionalOnMissingBean(WebMvcConfigurer.class)
    public WebMvcConfigurer webMvcConfigurer() {
        return new DefaultWebMvcConfigurer();
    }
    
    public interface WebMvcConfigurer {
        void addCorsMappings(CorsRegistry registry);
        void addViewControllers(ViewControllerRegistry registry);
    }
    
    public static class DefaultWebMvcConfigurer implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedHeaders("*")
                    .exposedHeaders("Location")
                    .allowedMethods("*");
        }

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("index");
            registry.addViewController("/{basePath:^(?!api|static|oauth).*$}/**").setViewName("index");
        }
    }
      
}
