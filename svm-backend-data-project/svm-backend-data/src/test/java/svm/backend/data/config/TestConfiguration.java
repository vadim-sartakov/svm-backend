package svm.backend.data.config;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import svm.backend.data.annotation.PredefinedProcessor;
import svm.backend.data.migration.service.MigrationExecutor;

@Configuration
public class TestConfiguration extends WebMvcConfigurerAdapter {
    
    @MockBean private MigrationExecutor migrationExecutor;
    @MockBean private PredefinedProcessor predefinedProcessor;
    @Autowired private LocalValidatorFactoryBean validator;
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.US);
        return messageSource;
    }
    
    @Override
    public Validator getValidator() {
        validator.setValidationMessageSource(messageSource());
        return validator;
    }
    
}
