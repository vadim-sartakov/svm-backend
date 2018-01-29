package svm.backend.dao.migration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import svm.backend.dao.repository.DatabaseChangelogRepository;

public abstract class AbstractMigration implements InitializingBean {

    private static final Map<String, String> MIGRATIONS = new HashMap<>();

    @Autowired private DatabaseChangelogRepository databaseChangelogRepository;

    private static synchronized void addMigration(String id, AbstractMigration migration) {
        
    }
    
    public abstract String getId();
    public abstract void execute();
    
    @Override
    public void afterPropertiesSet() throws Exception {
        addMigration(this.getId(), this);
        this.execute();
    }
    
}
