package svm.backend.dao.datachange;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import svm.backend.dao.entity.DatabaseChangelog;
import svm.backend.dao.repository.DatabaseChangelogRepository;

public abstract class DataChange implements InitializingBean {

    @Autowired protected DatabaseChangelogRepository databaseChangelogRepository;
            
    @Override
    public void afterPropertiesSet() throws Exception {
        
        String id = getId();
        
        if (databaseChangelogRepository.findOne(id) != null)
            return;
        
        update();
        DatabaseChangelog changelogRecord = DatabaseChangelog.of(id);
        databaseChangelogRepository.save(changelogRecord);
        
    }
    
    public abstract String getId();
    public abstract void update();
    
}
