package svm.backend.web.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcAutoConfiguration extends WebMvcConfigurerAdapter {
        
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
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.ENGLISH);
        messageSource.addBasenames(
                "classpath:locale/messages",
                "classpath:locale/i18n_messages",
                "classpath:org/springframework/security/messages"
        );
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
    
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource((MessageSource) messageSource());
        return validator;
    }
      
}
