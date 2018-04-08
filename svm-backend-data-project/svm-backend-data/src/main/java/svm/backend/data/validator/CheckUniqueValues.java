package svm.backend.data.validator;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.support.Repositories;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import svm.backend.data.validator.UniqueValues.FieldSet;
import svm.backend.data.validator.UniqueValues.Field;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CheckUniqueValues implements ConstraintValidator<UniqueValues, Object> {

    private final Repositories repositories;
    private final ConfigurableBeanFactory beanFactory;
            
    private UniqueValues uniqueValues;
    
    private boolean isValid;
    private Object object;
    private ConstraintValidatorContext context;
    private EntityPath<?> rootPath;
    private QueryDslPredicateExecutor repository;

    @Override
    public void initialize(UniqueValues uniqueValues) {
        this.uniqueValues = uniqueValues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        
        this.isValid = true;
        this.object = object;
        this.context = context;
        
        Class<?> objectType = object.getClass();
        //this.rootPath = new PathBuilder<>(objectType, objectType.getSimpleName().toLowerCase());
        this.rootPath = SimpleEntityPathResolver.INSTANCE.createPath(objectType);
        try {
            repository = (QueryDslPredicateExecutor) repositories.getRepositoryFor(objectType);
        } catch(Exception e) {
            throw new RuntimeException("Unable to retrieve Query dsl repository of type " + objectType.getSimpleName());
        }
        
        if (uniqueValues.fieldSets().length > 0) {
            for (FieldSet currentFieldSet : uniqueValues.fieldSets())
                checkFieldSet(currentFieldSet);
        } else {
            checkFields(uniqueValues.fields());            
        }
                
        return isValid;
        
    }
    
    private void checkFieldSet(FieldSet fieldSet) {
        
        Predicate fieldSetPredicate = getFieldSetPredicate(fieldSet);
        if (!getDuplicate(fieldSetPredicate).iterator().hasNext())
            return;
        
        for (Field field : fieldSet.value())
            addError(field.value(), fieldSet.message());

    }
        
    private Predicate getFieldSetPredicate(FieldSet fieldSet) {
        BooleanBuilder predicate = new BooleanBuilder();
        for (Field field : fieldSet.value())
            predicate.and(getFieldPredicate(field));
        return predicate;
    }
    
    private Predicate getFieldPredicate(Field field) {
        
        String propertyName = field.value();
        
        String expression = beanFactory.resolveEmbeddedValue(field.ignoreCaseExpr());
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        boolean ignoreCase = exp.getValue(Boolean.class);
        
        Object value = PropertyAccessorFactory
                .forBeanPropertyAccess(object)
                .getPropertyValue(propertyName);

        Class<?> valueType = value.getClass();
        
        return valueType.equals(String.class) && ignoreCase ?
                Expressions.stringPath(rootPath, propertyName).equalsIgnoreCase((String) value) :
                Expressions.path(Object.class, rootPath, propertyName).eq(value);
        
    }
        
    private void addError(String field, String messageTemplate) {
        isValid = false;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
    
    /**
     * To set specific flush mode in JPA repository use
     * @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT"))
     * on repository method
     * This will prevent recursive validation checks in hibernate.
     * @param predicate
     * @return 
     */
    private Iterable getDuplicate(Predicate predicate) {
        return repository.findAll(predicate);        
    }
    
    private void checkFields(Field[] fields) {
                
        for (Field field : fields) {
            Predicate predicate = getFieldPredicate(field);
            if (!getDuplicate(predicate).iterator().hasNext())
                continue;
            addError(field.value(), field.message());
        }
        
    }
    
}
