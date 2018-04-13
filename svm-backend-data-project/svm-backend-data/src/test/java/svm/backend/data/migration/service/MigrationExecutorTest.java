/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.data.migration.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;

public class MigrationExecutorTest {
    
    private final MigrationRepository migrationRepository = Mockito.mock(MigrationRepository.class);
    
    @Test
    public void testUpdates() throws Exception {
        
        List<MigrationUpdate> updatesSorted = Arrays.asList(udateTask("0", 0), udateTask("1", 1), udateTask("2", 2));
        List<MigrationUpdate> updatesUnsorted = Arrays.asList(updatesSorted.get(2), updatesSorted.get(1), updatesSorted.get(0));
        
        Mockito.when(migrationRepository.findOne("0")).thenReturn(updatesSorted.get(0));
        
        MigrationExecutor instance = new MigrationExecutor(
                migrationRepository,
                Optional.ofNullable(updatesUnsorted), Optional.empty()
        );
        instance.afterPropertiesSet();
        
        InOrder inOrder = inOrder(updatesSorted.toArray());
        inOrder.verify(updatesSorted.get(0), times(0)).update();
        inOrder.verify(updatesSorted.get(1)).update();
        inOrder.verify(updatesSorted.get(2)).update();
        
    }
    
    @Test
    public void testRollbacks() throws Exception {
        
        List<MigrationRollback> rollbacksSorted = Arrays.asList(combinedTask("2", 2, true), combinedTask("1", 1, true), combinedTask("0", 0, false));
        List<MigrationRollback> rollbacksUnsorted = Arrays.asList(rollbacksSorted.get(2), rollbacksSorted.get(1), rollbacksSorted.get(0));
        
        Mockito.when(migrationRepository.findOne("2")).thenReturn(rollbacksSorted.get(0));
        Mockito.when(migrationRepository.findOne("1")).thenReturn(null);
        Mockito.when(migrationRepository.findOne("0")).thenReturn(rollbacksSorted.get(2));
        
        MigrationExecutor instance = new MigrationExecutor(
                migrationRepository,
                Optional.empty(),
                Optional.ofNullable(rollbacksUnsorted)
        );
        instance.afterPropertiesSet();
        
        InOrder inOrder = inOrder(rollbacksSorted.toArray());
        inOrder.verify(rollbacksSorted.get(0)).rollback();
        inOrder.verify(rollbacksSorted.get(1), times(0)).rollback();
        inOrder.verify(rollbacksSorted.get(2), times(0)).rollback();
        
    }
    
    private MigrationUpdate udateTask(String id, Integer order) {
        MigrationUpdate migration = Mockito.mock(MigrationUpdate.class);
        Mockito.when(migration.getId()).thenReturn(id);
        Mockito.when(migration.getExecutionOrder()).thenReturn(order);
        return migration;
    }
    
    private CombinedTask combinedTask(String id, Integer order, boolean shouldRollback) {
        CombinedTask migration = Mockito.mock(CombinedTask.class);
        Mockito.when(migration.getId()).thenReturn(id);
        Mockito.when(migration.getExecutionOrder()).thenReturn(order);
        Mockito.when(migration.shouldRollback()).thenReturn(shouldRollback);
        return migration;
    }
    
    public interface CombinedTask extends MigrationUpdate, MigrationRollback { }
        
}
