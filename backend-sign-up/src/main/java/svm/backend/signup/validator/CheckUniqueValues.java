package svm.backend.signup.validator;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import svm.backend.signup.validator.UniqueValues.UniqueValue;

public class CheckUniqueValues implements ConstraintValidator<UniqueValues, Object> {

    @PersistenceContext private EntityManager entityManager;
    
    private UniqueValues annotation;
    
    @Override
    public void initialize(UniqueValues annotation) {
        this.annotation = annotation;
    }
    
    protected boolean checkIsUnique(Object alreadyRegistered, String field, ConstraintValidatorContext context) {
        
        if (alreadyRegistered == null)
            return true;
        
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(field)
                .addConstraintViolation();

        return false;
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        
        boolean isValid = true;
        
        for (UniqueValue currentAnnotation : annotation.value()) {
            
            String propertyName = currentAnnotation.field();
            Class<?> objectType = object.getClass();
            Object value = PropertyAccessorFactory
                    .forBeanPropertyAccess(object)
                    .getPropertyValue(propertyName);
            Class<?> valueType = value.getClass();
            
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(currentAnnotation.ignoreCaseExpr());
            boolean ignoreCase = exp.getValue(Boolean.class);
            
            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
            PathBuilder<?> rootPath = new PathBuilder(objectType, "o");
            
            BooleanExpression propertyExpression = valueType.equals(String.class) && ignoreCase ?
                    rootPath.getString(propertyName).equalsIgnoreCase((String) value) :
                    rootPath.get(propertyName).eq(value);
            Object duplicate = queryFactory.selectFrom(rootPath)
                    .where(propertyExpression)
                    .fetchOne();
            
            if (duplicate != null) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(propertyName)
                        .addConstraintViolation();
            }
            
        }
        
        return isValid;
        
    }
    
}
