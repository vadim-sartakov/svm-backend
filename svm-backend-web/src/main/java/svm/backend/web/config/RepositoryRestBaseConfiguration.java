package svm.backend.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import svm.backend.web.config.WebMvcConfiguration.WebMvcConfigurer;

@Configuration
public class RepositoryRestBaseConfiguration extends RepositoryRestConfigurerAdapter {
        
    @Autowired private MessageSource messageSource;
    @Autowired private LocalValidatorFactoryBean validator;
    @Autowired private WebMvcConfigurer corsConfigurer;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validator.setValidationMessageSource(messageSource);
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        corsConfigurer.addCorsMappings(config.getCorsRegistry());
    }
    
}
