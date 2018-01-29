package svm.backend.dao.autoconfigure;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.dao.entity.DatabaseChangelog;
import svm.backend.dao.migration.Migration;
import svm.backend.dao.migration.UserCreator;
import svm.backend.dao.repository.DatabaseChangelogRepository;

@Configuration
public class MigrationAutoConfigration {

    public static class MigrationExecutor implements InitializingBean {
        
        @Autowired private DatabaseChangelogRepository databaseChangelogRepository;
        @Autowired(required = false) private List<Migration> migrations;

        @Override
        public void afterPropertiesSet() throws Exception {

            if (migrations == null)
                return;
            
            migrations.stream()
                    .filter(migration -> databaseChangelogRepository.findOne(migration.getId()) == null)
                    .forEach(migration -> {
                        migration.execute();
                        databaseChangelogRepository.save(
                                DatabaseChangelog.of(migration.getId(), "Migration bean")
                        );
                    });
            
        }
        
    }
    
    @Bean
    public UserCreator userPredefinedCreator() {
        return new UserCreator();
    }
    
    @Bean
    public MigrationExecutor migrationExecutor() {
        return new MigrationExecutor();
    }
    
}
