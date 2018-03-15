package svm.backend.data.entity.validator;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import javax.persistence.FlushModeType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import svm.backend.data.entity.UUIDEntity;
import svm.backend.data.entity.validator.UniqueValues.FieldSet;
import svm.backend.data.entity.validator.UniqueValues.Field;

@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class CheckUniqueValues implements ConstraintValidator<UniqueValues, Object> {

    private final JPAQueryFactory jpaQueryFactory;
    private final ConfigurableBeanFactory beanFactory;
        
    private UniqueValues uniqueValues;
    
    private boolean isValid;
    private ConstraintValidatorContext context;
    private Object object;
    private PathBuilder<?> rootPath;

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
        
        Class<?> rootType = getRootClass(object.getClass());
        this.rootPath = new PathBuilder<>(rootType, rootType.getSimpleName().toLowerCase());
        
        if (uniqueValues.fieldSets().length > 0) {
            for (FieldSet currentFieldSet : uniqueValues.fieldSets())
                checkFieldSet(currentFieldSet);
        } else {
            checkFields(uniqueValues.fields());            
        }
                
        return isValid;
        
    }
    
    private Class<?> getRootClass(Class<?> currentClass) {
        Class<?> superclass = currentClass.getSuperclass();
        if (superclass != null && superclass != Object.class && superclass != UUIDEntity.class) {
            return getRootClass(superclass);
        } else
            return currentClass;
    }
    
    private void checkFieldSet(FieldSet fieldSet) {
        
        Predicate fieldSetPredicate = getFieldSetPredicate(fieldSet);
        if (getDuplicate(fieldSetPredicate) == null)
            return;
        
        for (Field field : fieldSet.value())
            addError(field.value(), fieldSet.message());

    }
        
    private Predicate getFieldSetPredicate(FieldSet fieldSet) {
        BooleanBuilder predicate = new BooleanBuilder();
        Arrays.asList(fieldSet.value())
                .forEach(this::getFieldPredicate); 
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
                rootPath.getString(propertyName).equalsIgnoreCase((String) value) :
                rootPath.get(propertyName).eq(value);
        
    }
        
    private void addError(String field, String messageTemplate) {
        isValid = false;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
    
    private Object getDuplicate(Predicate predicate) {
        return jpaQueryFactory.selectFrom(rootPath)
                .setFlushMode(FlushModeType.COMMIT)
                .where(predicate)
                .fetchOne();
        
    }
    
    private void checkFields(Field[] fields) {
                
        for (Field field : fields) {
            Predicate predicate = getFieldPredicate(field);
            if (getDuplicate(predicate) == null)
                continue;
            addError(field.value(), field.message());
        }
        
    }
    
}
