package svm.backend.security.jpa.dao.migration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.security.jpa.dao.entity.Role;
import svm.backend.security.jpa.dao.entity.User;

public class Security_1_0_0 implements MigrationUpdate {
    
    @PersistenceContext private EntityManager entityManager;
    
    @Transactional
    @Override
    public void update() {
        Role.PREDEFINED.forEach(this::save);
        User.PREDEFINED.forEach(this::save);
    }
    
    private void save(String key, Object value) {
        entityManager.persist(entityManager.merge(value));
    }

    @Override
    public String getId() {
        return "security_1_0_0";
    }

    @Override
    public Integer getExecutionOrder() {
        return 0;
    }
    
}
