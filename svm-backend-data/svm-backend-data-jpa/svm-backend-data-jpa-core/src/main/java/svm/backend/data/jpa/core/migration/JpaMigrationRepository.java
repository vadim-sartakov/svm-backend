package svm.backend.data.jpa.core.migration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.core.migration.model.Migration;
import svm.backend.data.core.migration.service.MigrationRepository;

@Transactional
public class JpaMigrationRepository implements MigrationRepository {

    @PersistenceContext private EntityManager entityManager;
    
    @Override
    public Migration save(Migration migration) {
        return entityManager.merge(
                JpaMigration.builder()
                        .id(migration.getId())
                        .build()
        );
    }

    @Override
    public Migration findOne(String id) {
        return entityManager.find(JpaMigration.class, id);
    }

    @Override
    public void delete(String id) {
        entityManager.remove(findOne(id));
    }

    @Override
    public void delete(Migration migration) {
        entityManager.remove(migration);
    }

}
