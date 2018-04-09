package svm.backend.data.validator;

import svm.backend.data.validator.dao.SingleFieldEntity;
import svm.backend.data.validator.dao.MultipleFieldEntity;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import svm.backend.data.Application;
import svm.backend.data.config.RepositoriesConfig;
import svm.backend.data.validator.dao.TestGroup;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "ignoreCaseParam=false", classes = Application.class)
@Import({
    RepositoriesConfig.class,
    CheckUniqueValues.class
})
@AutoConfigureTestDatabase
@Transactional
// TODO: find the way to test messages
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
        
        entityManager.detach(testEntity);
        
        testEntity.setStringExact(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(2, violations.size());
                
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
        
        testEntity.setFirstName(testEntity.getFirstName().toLowerCase());
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(2, violations.size());
        
        entityManager.detach(testEntity);
        
        testEntity.setFirstName("");
        violations = validator.validate(testEntity, TestGroup.class);
        assertEquals(0, violations.size());
        
    }
    
}