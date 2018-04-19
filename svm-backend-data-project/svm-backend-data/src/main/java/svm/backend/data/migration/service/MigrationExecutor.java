package svm.backend.data.migration.service;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import svm.backend.data.migration.model.MigrationRollback;
import svm.backend.data.migration.model.MigrationUpdate;

/**
 * DataChanges will be executed by this service.
 * @author Sartakov
 */
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MigrationExecutor implements InitializingBean {
        
    private final Logger logger = LoggerFactory.getLogger(MigrationExecutor.class);
    
    private final MigrationRepository migrationRepository;
    private final Optional<List<MigrationUpdate>> updates;
    private final Optional<List<MigrationRollback>> rollbacks;

    @Override
    public void afterPropertiesSet() throws Exception {
        updates.ifPresent(list -> list.stream()
                .sorted(this::compareUpdates)
                .filter(this::shouldUpdate).forEach(this::update));
        rollbacks.ifPresent(list -> list.stream()
                .sorted(this::compareRollbacks)
                .filter(this::shouldRollback).forEach(this::rollback));
    }
    
    private int compareUpdates(MigrationUpdate objectOne, MigrationUpdate objectTwo) {
        return objectOne.getExecutionOrder().compareTo(objectTwo.getExecutionOrder());
    }
    
    private boolean shouldUpdate(MigrationUpdate dataChange) {
        return !(dataChange instanceof MigrationRollback
                && ((MigrationRollback) dataChange).shouldRollback())
                && migrationRepository.findOne(dataChange.getId()) == null;
    }

    private void update(MigrationUpdate updateTask) {
        updateTask.update();
        migrationRepository.save(updateTask);
        logger.info("Successfully applied migration {}", updateTask.getId());
    }
    
    private int compareRollbacks(MigrationRollback objectOne, MigrationRollback objectTwo) {
        return objectTwo.getExecutionOrder().compareTo(objectOne.getExecutionOrder());
    }
    
    private boolean shouldRollback(MigrationRollback dataChange) {
        return dataChange.shouldRollback() && migrationRepository.findOne(dataChange.getId()) != null;
    }
    
    private void rollback(MigrationRollback dataChange) {
        String id = dataChange.getId();
        dataChange.rollback();
        migrationRepository.delete(id);
        logger.info("Successfully rolled back migration {}", id);
    }

}
