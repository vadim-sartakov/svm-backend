package svm.backend.data.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.Repositories;

@Configuration
public class RepositoriesConfig {
    @Bean
    public Repositories repositories(ApplicationContext context) {
        return new Repositories(context);
    }
}
