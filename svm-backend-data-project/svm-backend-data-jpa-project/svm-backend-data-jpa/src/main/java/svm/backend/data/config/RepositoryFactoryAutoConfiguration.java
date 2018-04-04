package svm.backend.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Enables custom repository factory.
 * @author Sartakov
 */
@Configuration
@EnableJpaRepositories(value = "**.repository", repositoryFactoryBeanClass = RepositoryFactoryBean.class)
public class RepositoryFactoryAutoConfiguration {
      
}
