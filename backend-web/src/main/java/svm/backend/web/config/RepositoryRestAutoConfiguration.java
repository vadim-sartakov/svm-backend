package svm.backend.web.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class RepositoryRestAutoConfiguration extends RepositoryRestConfigurerAdapter implements InitializingBean {
    
    @Qualifier("requestMappingHandlerAdapter")
    @Autowired
    private RequestMappingHandlerAdapter requestMappingAdapter;
    
    @Qualifier("repositoryExporterHandlerAdapter")
    @Autowired
    private RequestMappingHandlerAdapter restAdapter;
    
    @Autowired private MessageSource messageSource;
    @Autowired private LocalValidatorFactoryBean validator;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validator.setValidationMessageSource(messageSource);
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        MvcAutoConfiguration.addLocalhostCors(config.getCorsRegistry());
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        restoreQueryDslArgumentResolver();
        restoreValidator();
    }
    
    private void restoreQueryDslArgumentResolver() {
        List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<>(restAdapter.getArgumentResolvers());            
        List<HandlerMethodArgumentResolver> requestMappingCustomArgumentResolvers = requestMappingAdapter.getCustomArgumentResolvers();
        newArgumentResolvers.addAll(getArgumentResolversInsertIndex(newArgumentResolvers), requestMappingCustomArgumentResolvers);
        restAdapter.setArgumentResolvers(newArgumentResolvers);
    }
    
    private void restoreValidator() {
        ConfigurableWebBindingInitializer webBindingInitializer = (ConfigurableWebBindingInitializer) restAdapter.getWebBindingInitializer();
        webBindingInitializer.setValidator(validator);
        restAdapter.setWebBindingInitializer(webBindingInitializer);
    }
    
    private int getArgumentResolversInsertIndex(List<HandlerMethodArgumentResolver> argumentResolvers) {
        for (int i = 0; i < argumentResolvers.size(); i++)
            if (argumentResolvers.get(i).getClass().getSimpleName().equals("QuerydslAwareRootResourceInformationHandlerMethodArgumentResolver"))
                return i + 1;
        throw new RuntimeException("Unable to find argument resolver insertion point");
    }
    
}
