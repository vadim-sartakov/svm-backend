package svm.backend.data.migration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.data.migration.model.MigrationUpdate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MigrationExecutorIT {
    
    @MockBean private MigrationRepository migrationRepository;
    @MockBean private MigrationUpdate migrationUpdate;
    @Autowired private MigrationExecutor migrationExecutor;    

    @Before
    public void setUp() {
        Mockito.when(migrationUpdate.getId()).thenReturn("0");
        Mockito.when(migrationUpdate.getOrder()).thenReturn(0);
    }
    
    @Test
    public void testAfterPropertiesSet() throws Exception {
        
    }
        
}
