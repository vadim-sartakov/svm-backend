package svm.backend.data.core.validator;

import svm.backend.data.core.dao.entity.MultipleFieldEntity;
import svm.backend.data.core.dao.entity.SingleFieldEntity;
import svm.backend.data.dao.entity.SingleFieldEntity;
import svm.backend.data.dao.entity.MultipleFieldEntity;
import com.querydsl.core.types.Predicate;
import java.util.Iterator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.support.Repositories;

@SuppressWarnings("unchecked")
public class CheckUniqueValuesTest {
        
    private final static String TEST_STRING = "aNdrEW";
    private final Repositories repositories = Mockito.mock(Repositories.class);
    
    private final Iterable<Object> emptyIterable = Mockito.mock(Iterable.class);
    private final Iterable<Object> populatedIterable = Mockito.mock(Iterable.class);
    private final Iterator<Object> emptyIterator = Mockito.mock(Iterator.class);
    private final Iterator<Object> populatedIterator = Mockito.mock(Iterator.class);
    
    private final QueryDslPredicateExecutor<Object> repository = Mockito.mock(QueryDslPredicateExecutor.class);
    
    private final ConfigurableBeanFactory beanFactory = Mockito.mock(ConfigurableBeanFactory.class);
    private final CheckUniqueValues instance = new CheckUniqueValues(repositories, beanFactory);
    
    private final ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
    private final ConstraintViolationBuilder contextBuilder = Mockito.mock(ConstraintViolationBuilder.class);
        
    public CheckUniqueValuesTest() {
        
        Mockito.when(emptyIterable.iterator()).thenReturn(emptyIterator);
        Mockito.when(populatedIterable.iterator()).thenReturn(populatedIterator);
        Mockito.when(emptyIterator.hasNext()).thenReturn(false);
        Mockito.when(populatedIterator.hasNext()).thenReturn(true);
        
        Mockito.when(beanFactory.resolveEmbeddedValue(anyString())).thenReturn("true");
        Mockito.when(contextBuilder.addPropertyNode(anyString()))
                .thenReturn(Mockito.mock(NodeBuilderCustomizableContext.class));
        Mockito.when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(contextBuilder);
        Mockito.when(repositories.getRepositoryFor(anyObject())).thenReturn(repository);
        Mockito.when(repository.findAll(any(Predicate.class))).thenReturn(emptyIterable);
        
    }
    
    @Test
    public void testSingleFields() {
        
        SingleFieldEntity testEntity = SingleFieldEntity.builder()
            .stringExact(TEST_STRING)
            .stringIgnoreCase(TEST_STRING)
            .uniqueNumber(123)
            .build();
       
        instance.initialize(testEntity.getClass().getAnnotation(UniqueValues.class));
                
        assertEquals(true, instance.isValid(testEntity, context));
        Mockito.verify(contextBuilder, times(0)).addPropertyNode(anyString());
        
        Mockito.when(repository.findAll(any(Predicate.class)))
                .thenReturn(populatedIterable, populatedIterable, emptyIterable);
        
        assertEquals(false, instance.isValid(testEntity, context));
        
        Mockito.verify(contextBuilder, times(2)).addPropertyNode(anyString());
        Mockito.verify(contextBuilder, times(1)).addPropertyNode("stringIgnoreCase");
        Mockito.verify(contextBuilder, times(1)).addPropertyNode("stringExact");
        
    }

    @Test
    public void testFieldSet() {
        
        MultipleFieldEntity testEntity = MultipleFieldEntity.builder()
                .firstName(TEST_STRING)
                .lastName(TEST_STRING)
                .build();
        
        instance.initialize(testEntity.getClass().getAnnotation(UniqueValues.class));
        
        assertEquals(true, instance.isValid(testEntity, context));
        Mockito.verify(contextBuilder, times(0)).addPropertyNode(anyString());
        
        Mockito.when(repository.findAll(any(Predicate.class)))
                .thenReturn(populatedIterable);
        
        assertEquals(false, instance.isValid(testEntity, context));
        Mockito.verify(contextBuilder, times(2)).addPropertyNode(anyString());
        Mockito.verify(contextBuilder, times(1)).addPropertyNode("firstName");
        Mockito.verify(contextBuilder, times(1)).addPropertyNode("lastName");
        
    }
    
}
