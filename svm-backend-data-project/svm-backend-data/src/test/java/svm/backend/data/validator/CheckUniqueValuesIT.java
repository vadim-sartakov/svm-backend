package svm.backend.data.validator;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.data.Application;
import svm.backend.data.config.RepositoriesConfig;

/*RunWith(SpringRunner.class)
@SpringBootTest(properties = "ignoreCaseParam=false", classes = Application.class)
@Import({ RepositoriesConfig.class, CheckUniqueValues.class })
@AutoConfigureTestDatabase*/
public class CheckUniqueValuesIT {
    
    private final static String TEST_STRING = "aNdrEW";
    @Autowired private Validator validator;
    private Set<ConstraintViolation<Object>> violations;    
    
    //@Test
    public void testSingleFields() {
                
        SingleFieldEntity testEntity = SingleFieldEntity.builder()
            .stringExact(TEST_STRING)
            .stringIgnoreCase(TEST_STRING)
            .uniqueNumber(123)
            .build();
        
        violations = validator.validate(testEntity);
        assertEquals(violations.size(), 0);
        
        //entityManager.persist(testEntity);
        //entityManager.flush();
        
        testEntity.setStringIgnoreCase(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity);
        assertEquals(3, violations.size());
        
        testEntity.setStringExact(TEST_STRING.toLowerCase());
        violations = validator.validate(testEntity);
        assertEquals(2, violations.size());
                
    }
        
    //@Test
    public void testFieldSet() {
        
        MultipleFieldEntity testEntity = MultipleFieldEntity.builder()
                .firstName(TEST_STRING)
                .lastName(TEST_STRING)
                .build();
        
        violations = validator.validate(testEntity);
        assertEquals(violations.size(), 0);
        
        //entityManager.persist(testEntity);
        //entityManager.flush();
        
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
