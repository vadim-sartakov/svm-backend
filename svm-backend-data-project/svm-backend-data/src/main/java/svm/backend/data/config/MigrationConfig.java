package svm.backend.data.config;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.migration.service.MigrationExecutor;
import svm.backend.data.migration.service.MigrationRepository;

@Configuration
public class MigrationConfig {
    @Bean
    public MigrationExecutor migrationExecutor(MigrationRepository migrationRepository,
            Optional<List<MigrationUpdate>> updates,
            Optional<List<MigrationRollback>> rollbacks) {
        return new MigrationExecutor(migrationRepository, updates, rollbacks);
    }
}
