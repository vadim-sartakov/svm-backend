package svm.backend.data.core.validator;

import svm.backend.data.core.Application;
import svm.backend.data.core.dao.entity.MultipleFieldEntity;
import svm.backend.data.core.dao.entity.SingleFieldEntity;
import svm.backend.data.core.dao.entity.TestGroup;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "ignoreCaseParam=false", classes = Application.class)
@AutoConfigureTestDatabase
@Transactional
public class CheckUniqueValuesIT {
    
    private final static String TEST_STRING = "aNdrEW";
    
    @Autowired private LocalValidatorFactoryBean validator;
    @PersistenceContext private EntityManager entityManager;
    
    private Set<ConstraintViolation<Object>> violations;
        
    @Test
    public void testSingleFields() {

        SingleFieldEntity testEntity = SingleFieldEntity.builder()
            .stringExact(TEST_STRING)
            .stringIgnoreCase(TEST_STRING)
            .uniqueNumber(123)
            .build();

        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(0, violations.size());
        
        entityManager.persist(testEntity);
        entityManager.flush();
        
        testEntity.setStringIgnoreCase(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(3, violations.size());
        assertEquals("value is not unique", violations.iterator().next().getMessage());
        
        entityManager.persist(testEntity);
        entityManager.flush();
        
        entityManager.detach(testEntity);
        
        testEntity.setStringExact(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(2, violations.size());
        assertEquals("value is not unique", violations.iterator().next().getMessage());
                
    }
            
    @Test
    public void testFieldSet() {
        
        MultipleFieldEntity testEntity = MultipleFieldEntity.builder()
                .firstName(TEST_STRING)
                .lastName(TEST_STRING)
                .build();
        
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(0, violations.size());
        
        entityManager.persist(testEntity);
        entityManager.flush();
        
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(2, violations.size());
        assertEquals("values are not unique", violations.iterator().next().getMessage());
        
        testEntity.setFirstName(testEntity.getFirstName().toLowerCase());
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(2, violations.size());
        assertEquals("values are not unique", violations.iterator().next().getMessage());
        
        entityManager.detach(testEntity);
        
        testEntity.setFirstName("");
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(0, violations.size());
        
    }
    
}
