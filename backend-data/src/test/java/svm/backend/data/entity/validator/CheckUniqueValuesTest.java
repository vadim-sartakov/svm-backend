package svm.backend.data.entity.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import svm.backend.data.entity.SingleFieldEntity;
import javax.validation.Validator;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class CheckUniqueValuesTest {
    
    @PersistenceContext private EntityManager entityManager;
    @Autowired private Validator validator;
    
    @Test
    public void testIsValid() {
        SingleFieldEntity testEntity = new SingleFieldEntity("Andrew");
        assertEquals(validator.validate(testEntity).size(), 0);
        entityManager.persist(testEntity);
        assertEquals(validator.validate(testEntity).size(), 1);
    }
     
}
