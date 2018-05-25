package svm.backend.core.config;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import svm.backend.core.service.MessagesBasenameProvider;

@Configuration
public class CoreAutoConfiguration {
    
    @Autowired(required = false) private List<MessagesBasenameProvider> basenameProviders;
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        Locale.setDefault(Locale.US);
        if (basenameProviders != null)
            basenameProviders.forEach(provider -> messageSource.addBasenames(provider.getBasename()));
        return messageSource;
    }
    
}
