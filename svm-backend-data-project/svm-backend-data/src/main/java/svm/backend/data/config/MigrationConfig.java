package svm.backend.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import svm.backend.data.migration.service.MigrationExecutor;

@Configuration
@Import(MigrationExecutor.class)
public class MigrationConfig {

}
