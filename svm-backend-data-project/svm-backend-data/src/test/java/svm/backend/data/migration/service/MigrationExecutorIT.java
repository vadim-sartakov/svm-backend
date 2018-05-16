package svm.backend.data.migration.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.data.Application;
import svm.backend.data.migration.model.MigrationUpdate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MigrationExecutorIT {

    @MockBean private MigrationUpdate migrationUpdate;

    @Before
    public void setUp() {
        Mockito.when(migrationUpdate.getId()).thenReturn("0");
        Mockito.when(migrationUpdate.getExecutionOrder()).thenReturn(0);
    }
    
    @Test
    public void testAfterPropertiesSet() throws Exception {
        
    }
        
}
