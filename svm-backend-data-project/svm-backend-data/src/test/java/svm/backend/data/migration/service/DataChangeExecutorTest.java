/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.migration.service;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;

public class DataChangeExecutorTest {
    
    private final List<CombinedTask> combinedTasks =
            Arrays.asList(combinedTask("0", 0, false), combinedTask("1", 1, true), combinedTask("2", 2, true));
    
    @Test
    public void testMigration() throws Exception {

        MigrationRepository migrationRepository = Mockito.mock(MigrationRepository.class);
        List<MigrationUpdate> updates = Arrays.asList(udateTask("0", 0), udateTask("1", 1), udateTask("2", 2));
        List<MigrationRollback> rollbacks = Arrays.asList(rollbackTask("0", 0, false), rollbackTask("1", 1, true));
        
        MigrationExecutor instance = new MigrationExecutor(migrationRepository, updates, rollbacks);
        instance.afterPropertiesSet();
        
        
        
    }
    
    private MigrationUpdate udateTask(String id, Integer order) {
        MigrationUpdate migration = Mockito.mock(MigrationUpdate.class);
        Mockito.when(migration.getId()).thenReturn(id);
        Mockito.when(migration.getOrder()).thenReturn(order);
        return migration;
    }
    
    private MigrationRollback rollbackTask(String id, Integer order, boolean shouldRollback) {
        MigrationRollback migration = Mockito.mock(MigrationRollback.class);
        Mockito.when(migration.getId()).thenReturn(id);
        Mockito.when(migration.getOrder()).thenReturn(order);
        Mockito.when(migration.shouldRollback()).thenReturn(shouldRollback);
        return migration;
    }
    
    private CombinedTask combinedTask(String id, Integer order, boolean shouldRollback) {
        CombinedTask migration = Mockito.mock(CombinedTask.class);
        Mockito.when(migration.getId()).thenReturn(id);
        Mockito.when(migration.getOrder()).thenReturn(order);
        Mockito.when(migration.shouldRollback()).thenReturn(shouldRollback);
        return migration;
    }
    
    public interface CombinedTask extends MigrationUpdate, MigrationRollback { }
        
}
