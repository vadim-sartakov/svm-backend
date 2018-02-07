package svm.backend.datachange.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.datachange.DataChange;
import svm.backend.datachange.DataChangeRollback;
import svm.backend.datachange.DataChangeUpdate;
import svm.backend.datachange.entity.DatabaseChangelog;
import svm.backend.datachange.repository.DatabaseChangelogRepository;

/**
 * DataChanges will be executed by this service.
 * @author Sartakov
 */
public class DataChangeExecutor implements InitializingBean {
        
    private final Logger logger = LoggerFactory.getLogger(DataChangeExecutor.class);
    @Autowired private DatabaseChangelogRepository databaseChangelogRepository;
    @Autowired private List<DataChange> dataChanges;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        execute();
    }
    
    public void execute() {
        
        dataChanges.forEach(dataChange -> {
            
            if (dataChange instanceof DataChangeUpdate)
                update((DataChangeUpdate) dataChange);
            
            if (dataChange instanceof DataChangeRollback)                
                rollback((DataChangeRollback) dataChange);

        });
        
    }
    
    @Transactional
    private void update(DataChangeUpdate updateTask) {
        
        String id = updateTask.getId();
        if (databaseChangelogRepository.findOne(id) != null ||
                updateTask instanceof DataChangeRollback &&
                ((DataChangeRollback) updateTask).shouldRollback())
            return;
        
        updateTask.update();
        
        DatabaseChangelog changelogRecord = DatabaseChangelog.of(id);
        databaseChangelogRepository.save(changelogRecord);
        
        logger.info("Successfully applied data change {}", id);
        
    }
    
    @Transactional
    private void rollback(DataChangeRollback rollbackTask) {
        
        String id = rollbackTask.getId();
        DatabaseChangelog changelogRecord = databaseChangelogRepository.findOne(id);
        if (changelogRecord == null || !rollbackTask.shouldRollback())
            return;
        
        rollbackTask.rollback();

        databaseChangelogRepository.delete(changelogRecord);
        
        logger.info("Successfully rolled back data change {}", id);
        
    }

}
