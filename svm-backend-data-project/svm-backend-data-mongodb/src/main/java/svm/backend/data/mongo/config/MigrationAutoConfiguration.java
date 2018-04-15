package svm.backend.data.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.migration.service.MigrationRepository;
import svm.backend.data.mongo.migration.MongoMigrationRepository;

@Configuration
public class MigrationAutoConfiguration {
    @Bean
    public MigrationRepository migrationRepository() {
        return new MongoMigrationRepository();
    }
}
