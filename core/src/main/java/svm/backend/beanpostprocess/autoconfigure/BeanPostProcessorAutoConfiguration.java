package svm.backend.beanpostprocess.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPostProcessorAutoConfiguration {
    
    @Bean
    public BeanPostProcessorService beanPostProcessorService() {
        return new BeanPostProcessorService();
    }
    
    /*@Bean
    public RepositoryRestPostProcessor repositoryRestPostProcessor() {
        return new RepositoryRestPostProcessor();
    }*/
    
}
