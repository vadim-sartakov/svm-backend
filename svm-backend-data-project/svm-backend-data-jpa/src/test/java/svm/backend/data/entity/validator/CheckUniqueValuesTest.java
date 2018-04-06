package svm.backend.data.entity.validator;

import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import svm.backend.data.entity.SingleFieldEntity;
import javax.validation.Validator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import svm.backend.data.entity.MultipleFieldEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "ignoreCaseParam=false")
@AutoConfigureTestDatabase
@Transactional
public class CheckUniqueValuesTest {
    
    private final static String TEST_STRING = "aNdrEW";
    
    @PersistenceContext private EntityManager entityManager;
    @Autowired private Validator validator;
    
    private Set<ConstraintViolation<Object>> violations;    
    
    @Test
    public void testSingleFields() {
                
        SingleFieldEntity testEntity = SingleFieldEntity.builder()
            .stringExact(TEST_STRING)
            .stringIgnoreCase(TEST_STRING)
            .uniqueNumber(123)
            .build();
        
        violations = validator.validate(testEntity);
        assertEquals(violations.size(), 0);
        
        entityManager.persist(testEntity);
        entityManager.flush();
        
        testEntity.setStringIgnoreCase(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity);
        assertEquals(3, violations.size());
        
        testEntity.setStringExact(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity);
        assertEquals(2, violations.size());
                
    }
        
    @Test
    public void testFieldSet() {
        
        MultipleFieldEntity testEntity = MultipleFieldEntity.builder()
                .firstName(TEST_STRING)
                .lastName(TEST_STRING)
                .build();
        
        violations = validator.validate(testEntity);
        assertEquals(violations.size(), 0);
        
        entityManager.persist(testEntity);
        entityManager.flush();
        
        violations = validator.validate(testEntity);
        assertEquals(2, violations.size());
        
        testEntity.setFirstName(testEntity.getFirstName().toLowerCase());
        violations = validator.validate(testEntity);
        assertEquals(2, violations.size());
        
        testEntity.setFirstName("");
        violations = validator.validate(testEntity);
        assertEquals(0, violations.size());
        
    }
    
}
