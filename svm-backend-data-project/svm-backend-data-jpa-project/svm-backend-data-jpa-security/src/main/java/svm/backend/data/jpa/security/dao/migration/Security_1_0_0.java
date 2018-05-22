package svm.backend.data.jpa.security.dao.migration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import svm.backend.data.migration.model.MigrationUpdate;
import svm.backend.data.jpa.security.dao.entity.Oauth2Client;
import svm.backend.data.jpa.security.dao.entity.Role;
import svm.backend.data.jpa.security.dao.entity.User;

public class Security_1_0_0 implements MigrationUpdate {
    
    @PersistenceContext private EntityManager entityManager;
    
    @Transactional
    @Override
    public void update() {
        ReflectionUtils.doWithFields(Role.class, this::save, field -> filter(Role.class, field));
        ReflectionUtils.doWithFields(User.class, this::save, field -> filter(User.class, field));
        ReflectionUtils.doWithFields(Oauth2Client.class, this::save, field -> filter(Oauth2Client.class, field));
    }
    
    private boolean filter(Class<?> type, Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers) &&
                Modifier.isStatic(modifiers) &&
                Modifier.isFinal(modifiers) &&
                field.getType().equals(type);
    }
    
    private void save(Field field) {
        try {
            entityManager.persist(entityManager.merge(field.get(null)));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getId() {
        return "security-1.0.0";
    }

    @Override
    public Integer getExecutionOrder() {
        return 0;
    }
    
}
