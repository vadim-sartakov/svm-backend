package svm.backend.data.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckUniqueValues.class)
public @interface UniqueValues {
    
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    Field[] fields() default { };
    FieldSet[] fieldSets() default { };
    
    @Retention(RUNTIME)
    public @interface FieldSet {
        Field[] value();
        String message() default "{svm.backend.data.validator.UniqueValues.FieldSet.message}";
    }
    
    @Retention(RUNTIME)
    public @interface Field {
        String value();
        String message() default "{svm.backend.data.validator.UniqueValues.Field.message}";
        /**
         * Spel expression to determine either value should be selected ignore case or not.
         * May contain spring boot parameter reference.
         * Applicable only for strings. Will be ignored for other types.
         * @return 
         */
        String ignoreCaseExpr() default "true";
    }
            
}
