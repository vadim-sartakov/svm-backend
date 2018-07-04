package svm.backend.data.mongo.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.core.migration.service.MigrationRepository;
import svm.backend.data.mongo.core.migration.MongoMigrationRepository;

@Configuration
public class MigrationAutoConfiguration {
    @Bean
    public MigrationRepository migrationRepository() {
        return new MongoMigrationRepository();
    }
}
