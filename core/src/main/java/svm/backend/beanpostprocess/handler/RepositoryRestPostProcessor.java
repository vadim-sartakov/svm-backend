package svm.backend.beanpostprocess.handler;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.RepositoryRestHandlerAdapter;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Restores argument resolvers and validators of repository rest handler adapter.
 * @author Vadim Sartakov
 */
public class RepositoryRestPostProcessor implements PostProcessorHandler<RepositoryRestHandlerAdapter> {

    @Qualifier("requestMappingHandlerAdapter")
    @Autowired
    private RequestMappingHandlerAdapter requestMappingAdapter;
    
    @Autowired
    private Validator validator;
    
    private List<HandlerMethodArgumentResolver> requestMappingCustomArgumentResolvers;
    private RepositoryRestHandlerAdapter restAdapter;
    
    @Override
    public String getQualifier() {
        return "repositoryExporterHandlerAdapter";
    }
    
    @Override
    public RepositoryRestHandlerAdapter postProcessBeforeInitialization(RepositoryRestHandlerAdapter bean, String beanName) {
        restAdapter = bean;
        requestMappingCustomArgumentResolvers = requestMappingAdapter.getCustomArgumentResolvers();            
        return restAdapter;
    }

    @Override
    public RepositoryRestHandlerAdapter postProcessAfterInitialization(RepositoryRestHandlerAdapter bean, String beanName) {
        
        restAdapter = bean;
        restoreArgumentResolvers();
        restoreValidator();
        
        return restAdapter;   
        
    }
    
    private void restoreArgumentResolvers() {
        
        List<HandlerMethodArgumentResolver> restDataArgumentResolvers = restAdapter.getArgumentResolvers();            
        List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<>(restDataArgumentResolvers);            

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
