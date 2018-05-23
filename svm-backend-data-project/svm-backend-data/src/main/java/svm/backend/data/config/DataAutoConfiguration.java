package svm.backend.data.config;

import java.util.List;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.repository.support.Repositories;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.migration.service.MigrationExecutor;
import svm.backend.data.migration.service.MigrationRepository;

@Configuration
@ConditionalOnMissingBean(DataAutoConfiguration.class)
@ComponentScan("svm.backend.data.security")
@PropertySource("classpath:properties/data/application.properties")
@EnableConfigurationProperties(DataProperties.class)
@Import({ MessagesConfiguration.class, PredefinedConfiguration.class })
public class DataAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(Repositories.class)
    public Repositories repositories(ApplicationContext context) {
        return new Repositories(context);
    }
    
    @Bean
    @ConditionalOnProperty(name = "svm.backend.data.update", matchIfMissing = true)
    public MigrationExecutor migrationExecutor(MigrationRepository migrationRepository,
            Optional<List<MigrationUpdate>> updates,
            Optional<List<MigrationRollback>> rollbacks) {
        return new MigrationExecutor(migrationRepository, updates, rollbacks);
    }
    
}
