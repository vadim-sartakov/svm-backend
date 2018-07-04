package svm.backend.data.jpa.core.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.jpa.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class EntityIT {
    
    @PersistenceContext private EntityManager entityManager;
    
    @Test
    public void testGeneratedValues() {
        TestEntity testEntity = new TestEntity();
        entityManager.persist(testEntity);
        assertNotNull(testEntity.getId());
        assertNotNull(testEntity.getCreatedAt());
        assertNotNull(testEntity.getUpdatedAt());
    }
    
}
