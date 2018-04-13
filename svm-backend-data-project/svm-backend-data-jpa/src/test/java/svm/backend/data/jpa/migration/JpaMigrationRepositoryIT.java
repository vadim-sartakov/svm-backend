package svm.backend.data.jpa.migration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.jpa.Application;
import svm.backend.data.jpa.config.BaseMessagesConfig;
import svm.backend.data.jpa.migration.JpaMigrationRepositoryIT.RollbackMigration;
import svm.backend.data.jpa.migration.JpaMigrationRepositoryIT.UpdateMigration;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.migration.service.MigrationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, properties = "shouldRollback=true")
@Import({ UpdateMigration.class, RollbackMigration.class, BaseMessagesConfig.class })
@Transactional
public class JpaMigrationRepositoryIT {
        
    public static final String ID_ONE = "1.0.0";
    public static final String ID_TWO = "1.0.1";
    
    @Autowired private MigrationRepository migrationRepository;
    
    @Test
    public void testMigrations() {
        assertNotNull(migrationRepository.findOne(ID_ONE));
        assertNull(migrationRepository.findOne(ID_TWO));
    }
        
    public static class UpdateMigration implements MigrationUpdate {

        @Override
        public String getId() {
            return ID_ONE;
        }

        @Override
        public Integer getExecutionOrder() {
            return 0;
        }

        @Override
        public void update() {
            System.out.println("Applying migration " + getId());
        }
        
    }
    
    public static class RollbackMigration implements MigrationUpdate, MigrationRollback {

        @Value("${shouldRollback}")
        private boolean shouldRollback;
        
        @Override
        public String getId() {
            return ID_TWO;
        }

        @Override
        public Integer getExecutionOrder() {
            return 0;
        }

        @Override
        public void update() {
            System.out.println("Applying migration " + getId());
        }
        
        @Override
        public boolean shouldRollback() {
            return shouldRollback;
        }
        
        @Override
        public void rollback() {
            System.out.println("Rolling back migration " + getId());
        }
        
    }
    
}
