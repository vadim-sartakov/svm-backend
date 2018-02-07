package svm.backend.core.beanpostprocess.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import svm.backend.core.beanpostprocess.handler.PostProcessorHandler;
import svm.backend.core.beanpostprocess.handler.PostProcessorHandler;

public class BeanPostProcessorService implements BeanPostProcessor {
        
    private Map<String, PostProcessorHandler> handlers = new HashMap<>();
            
    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        PostProcessorHandler handler = handlers.get(beanName);
        return handler == null ? bean : handler.postProcessBeforeInitialization(bean, beanName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        PostProcessorHandler handler = handlers.get(beanName);
        return handler == null ? bean : handler.postProcessAfterInitialization(bean, beanName);
    }
    
    @Autowired(required = false)
    public void setHandlers(Set<PostProcessorHandler> handlers) {
        if (handlers == null)
            return;
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(PostProcessorHandler::getQualifier, Function.identity()));
    }

}
