package svm.backend.data.migration.service;

import java.util.List;
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
@AllArgsConstructor(onConstructor_= @Autowired(required = false))
public class MigrationExecutor implements InitializingBean {
        
    private final Logger logger = LoggerFactory.getLogger(MigrationExecutor.class);
    
    private final MigrationRepository migrationRepository;
    private final List<MigrationUpdate> updates;
    private final List<MigrationRollback> rollbacks;

    @Override
    public void afterPropertiesSet() throws Exception {
        updates.stream().sorted((objectOne, objectTwo) -> objectOne.getOrder().compareTo(objectTwo.getOrder()))
                .filter(this::shouldUpdate).forEach(this::update);
        rollbacks.stream().sorted((objectOne, objectTwo) -> objectTwo.getOrder().compareTo(objectOne.getOrder()))
               .filter(this::shouldRollback).forEach(this::rollback);
    }
    
    private boolean shouldUpdate(MigrationUpdate dataChange) {       
        return !(dataChange instanceof MigrationRollback
                && ((MigrationRollback) dataChange).shouldRollback())
                && migrationRepository.findById(dataChange.getId()) == null;
    }

    private void update(MigrationUpdate updateTask) {
        String id = updateTask.getId();
        updateTask.update();
        migrationRepository.save(updateTask);
        logger.info("Successfully applied migration {}", id);
    }
    
    private boolean shouldRollback(MigrationRollback dataChange) {
        return dataChange.shouldRollback() && migrationRepository.findById(dataChange.getId()) == null;
    }
    
    private void rollback(MigrationRollback dataChange) {
        String id = dataChange.getId();
        dataChange.rollback();
        migrationRepository.delete(id);
        logger.info("Successfully rolled back migration {}", id);
    }

}
