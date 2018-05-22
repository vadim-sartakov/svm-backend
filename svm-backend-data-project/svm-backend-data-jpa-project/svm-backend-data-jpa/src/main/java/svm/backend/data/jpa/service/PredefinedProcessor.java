package svm.backend.data.jpa.service;

import java.lang.reflect.Field;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ReflectionUtils;
import svm.backend.data.annotation.Predefined;

/**
 * Performs search of predefined elements and
 * persists them in database.
 */
public class PredefinedProcessor implements InitializingBean {

    @PersistenceContext private EntityManager entityManager;
    @Autowired private PlatformTransactionManager transactionManager;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(this::executeInTransaction);
    }
    
    private TransactionStatus executeInTransaction(TransactionStatus status) {
        entityManager.getMetamodel().getEntities().stream()
                .forEach(this::processEntityType);
        return status;
    }
    
    private void processEntityType(EntityType<?> entityType) {
        ReflectionUtils.doWithFields(entityType.getJavaType(),
                this::savePredefined, this::filterField);
    }
    
    private boolean filterField(Field field) {
        return field.getAnnotation(Predefined.class) != null;
    }
    
    private void savePredefined(Field field) {
        Object value = ReflectionUtils.getField(field, null);
        entityManager.persist(entityManager.merge(value));
    }
    
}
