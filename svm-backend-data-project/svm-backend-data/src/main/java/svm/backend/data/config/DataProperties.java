package svm.backend.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("svm.backend.data")
public class DataProperties {
    /**
     * Whether application runs in update mode or not.
     * This mode includes liquibase bean run, predefined processing and migration execution.
     */
    private boolean update = true;
}
