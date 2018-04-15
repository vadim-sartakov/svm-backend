/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.mongo.migration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.migration.service.MigrationRepository;
import svm.backend.data.mongo.Application;
import svm.backend.data.mongo.migration.MongoMigrationRepositoryIT.RollbackMigration;
import svm.backend.data.mongo.migration.MongoMigrationRepositoryIT.UpdateMigration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, properties = "shouldRollback=true")
@Import({ UpdateMigration.class, RollbackMigration.class })
public class MongoMigrationRepositoryIT {
        
    public static final String ID_ONE = "1.0.0";
    public static final String ID_TWO = "1.0.1";
    
    @Autowired private MigrationRepository migrationRepository;
    @MockBean private ReloadableResourceBundleMessageSource messageSource;
    
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