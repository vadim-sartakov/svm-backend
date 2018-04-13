package svm.backend.data.jpa.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.jpa.Application;
import svm.backend.data.jpa.config.BaseMessagesConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Import(BaseMessagesConfig.class)
public class EntityIT {
    
    @PersistenceContext private EntityManager entityManager;
    
    @Test
    public void testEntities() {
        TestEntity testEntity = new TestEntity();
        entityManager.persist(testEntity);
        assertNotNull(testEntity.getId());
        assertNotNull(testEntity.getCreatedAt());
        assertNotNull(testEntity.getUpdatedAt());
    }
    
}
