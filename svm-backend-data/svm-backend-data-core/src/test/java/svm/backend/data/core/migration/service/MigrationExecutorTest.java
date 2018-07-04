package svm.backend.data.core.migration.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import svm.backend.data.core.migration.model.MigrationRollback;
import svm.backend.data.core.migration.model.MigrationUpdate;

public class MigrationExecutorTest {
    
    private final MigrationRepository migrationRepository = Mockito.mock(MigrationRepository.class);
    
    @Test
    public void testUpdates() throws Exception {
        
        List<MigrationUpdate> updatesSorted = Arrays.asList(udateTask("0"), udateTask("1"), udateTask("2"));
        
        Mockito.when(migrationRepository.findOne("0")).thenReturn(updatesSorted.get(0));
        
        MigrationExecutor instance = new MigrationExecutor(
                migrationRepository,
                Optional.ofNullable(updatesSorted), Optional.empty()
        );
        instance.afterPropertiesSet();
        
        InOrder inOrder = inOrder(updatesSorted.toArray());
        inOrder.verify(updatesSorted.get(0), times(0)).update();
        inOrder.verify(updatesSorted.get(1)).update();
        inOrder.verify(updatesSorted.get(2)).update();
        
    }
    
    @Test
    public void testRollbacks() throws Exception {
        
        List<MigrationRollback> rollbacksSorted = Arrays.asList(combinedTask("2", true), combinedTask("1", true), combinedTask("0", false));
        
        Mockito.when(migrationRepository.findOne("2")).thenReturn(rollbacksSorted.get(0));
        Mockito.when(migrationRepository.findOne("1")).thenReturn(null);
        Mockito.when(migrationRepository.findOne("0")).thenReturn(rollbacksSorted.get(2));
        
        MigrationExecutor instance = new MigrationExecutor(
                migrationRepository,
                Optional.empty(),
                Optional.ofNullable(rollbacksSorted)
        );
        instance.afterPropertiesSet();
        
        InOrder inOrder = inOrder(rollbacksSorted.toArray());
        inOrder.verify(rollbacksSorted.get(0)).rollback();
        inOrder.verify(rollbacksSorted.get(1), times(0)).rollback();
        inOrder.verify(rollbacksSorted.get(2), times(0)).rollback();
        
    }
    
    private MigrationUpdate udateTask(String id) {
        MigrationUpdate migration = Mockito.mock(MigrationUpdate.class);
        Mockito.when(migration.getId()).thenReturn(id);
        return migration;
    }
    
    private CombinedTask combinedTask(String id, boolean shouldRollback) {
        CombinedTask migration = Mockito.mock(CombinedTask.class);
        Mockito.when(migration.getId()).thenReturn(id);
        Mockito.when(migration.shouldRollback()).thenReturn(shouldRollback);
        return migration;
    }
    
    public interface CombinedTask extends MigrationUpdate, MigrationRollback { }
        
}
