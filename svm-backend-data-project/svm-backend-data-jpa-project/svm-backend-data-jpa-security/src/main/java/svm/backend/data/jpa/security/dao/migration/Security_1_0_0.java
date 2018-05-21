package svm.backend.data.jpa.security.dao.migration;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.Role;
import svm.backend.data.jpa.security.dao.entity.User;

public class Security_1_0_0 implements MigrationUpdate {
    
    @PersistenceContext private EntityManager entityManager;
    
    @Transactional
    @Override
    public void update() {
        Role.PREDEFINED.forEach(this::save);
        User.PREDEFINED.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(User.SYSTEM))
                .forEach(this::save);
        Oauth2Client.PREDEFINED.forEach(this::save);
    }
    
    private void save(String key, Object value) {
        entityManager.persist(entityManager.merge(value));
    }
    
    private void save(Map.Entry<String, User> entry) {
        entityManager.persist(entityManager.merge(entry.getValue()));
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
