package svm.backend.data.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.support.Repositories;
import svm.backend.data.migration.service.MigrationExecutor;

@Configuration
@ConditionalOnMissingBean(DataAutoConfiguration.class)
@ComponentScan("svm.backend.data.security")
@Import({ MessagesConfiguration.class, MigrationExecutor.class })
public class DataAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(Repositories.class)
    public Repositories repositories(ApplicationContext context) {
        return new Repositories(context);
    }
    
}
