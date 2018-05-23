package svm.backend.data.jpa.service;

import java.lang.reflect.Field;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import svm.backend.data.annotation.Predefined;
import svm.backend.data.annotation.PredefinedProcessor;

public class JpaPredefinedProcessor implements PredefinedProcessor {

    private final Logger logger = LoggerFactory.getLogger(JpaPredefinedProcessor.class);
    @PersistenceContext private EntityManager entityManager;
    
    @Transactional
    @Override
    public void process() {
        logger.info("Processing predefined elements");
        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
        entityTypes.stream().forEach(this::processEntityType);
        logger.info("Sucessfully saved {} predefined elements", entityTypes.size());
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
