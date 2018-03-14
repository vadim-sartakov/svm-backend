package svm.backend.data.entity.validator;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import svm.backend.data.entity.UUIDEntity;
import svm.backend.data.entity.validator.UniqueValues.FieldSet;
import svm.backend.data.entity.validator.UniqueValues.Field;

public class CheckUniqueValues implements ConstraintValidator<UniqueValues, Object> {

    @PersistenceContext private EntityManager entityManager;
    @Autowired private ConfigurableBeanFactory beanFactory;
    
    private UniqueValues uniqueValues;
    
    private boolean isValid;
    private Object object;
    private ConstraintValidatorContext context;
    
    private Class<?> objectType;
    //private FieldSet fieldSet;
    //private Field field;
    private String propertyName;
    private boolean ignoreCase;
    
    @Override
    public void initialize(UniqueValues uniqueValues) {
        this.uniqueValues = uniqueValues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        
        this.isValid = true;

        this.object = object;
        this.objectType = object.getClass();
        this.context = context;
        
        if (uniqueValues.fieldSet().length > 0) {
            
            for (FieldSet currentFieldSet : uniqueValues.fieldSet()) {
                //this.fieldSet = currentFieldSet;
                checkFieldSet(currentFieldSet);
            }
            
        } else {
            checkFields(uniqueValues.value());            
        }
                
        return isValid;
        
    }
    
    private void checkFieldSet(FieldSet fieldSet) {
        
        checkFields(fieldSet.value());
        
        /*String expression = beanFactory.resolveEmbeddedValue(uniqueValue.ignoreCaseExpr());
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        this.ignoreCase = exp.getValue(Boolean.class);
        
        BooleanBuilder predicate = new BooleanBuilder();
        PathBuilder<?> rootPath = new PathBuilder(objectType, "root");
        for (String currentPropertyName : uniqueValue.fields()) {
            this.propertyName = currentPropertyName;
            predicate = getPredicate(rootPath, predicate);
        }
        
        if (getDuplicate(rootPath, predicate) == null)
            return;*/

        isValid = false;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(propertyName)
                .addConstraintViolation();
        
    }
    
    private void checkFields(Field[] fields) {
        for (Field currentField : fields)
            checkField(currentField);
    }
    
    private void checkField(Field field) {
        
    }

    private BooleanBuilder getPredicate(PathBuilder<?> rootPath, BooleanBuilder predicate) {

        Object value = PropertyAccessorFactory
                .forBeanPropertyAccess(object)
                .getPropertyValue(propertyName);
        Class<?> valueType = value.getClass();
        
        BooleanExpression currentPredicate = valueType.equals(String.class) && ignoreCase ?
                rootPath.getString(propertyName).equalsIgnoreCase((String) value) :
                rootPath.get(propertyName).eq(value);
        
        Class<?> superclass = rootPath.getType().getSuperclass();
        if (superclass != null && superclass != Object.class && superclass != UUIDEntity.class) {
            PathBuilder<?> superclassPath = new PathBuilder(superclass, superclass.getSimpleName().toLowerCase());
            currentPredicate.and(getPredicate(superclassPath, predicate));
        }
        
        return predicate.or(currentPredicate);
        
    }
    
    private Object getDuplicate(PathBuilder<?> rootPath, BooleanBuilder predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        
        return queryFactory.selectFrom(rootPath)
                .setFlushMode(FlushModeType.COMMIT)
                .where(predicate)
                .fetchOne();
    }
    
}
