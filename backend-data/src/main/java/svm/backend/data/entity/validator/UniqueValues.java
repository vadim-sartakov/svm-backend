package svm.backend.data.entity.validator;

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
    
    String message() default "{svm.backend.signup.validator.UniqueValue.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    Field[] value();
    FieldSet[] fieldSet() default { };
    
    @Retention(RUNTIME)
    public @interface FieldSet {
        Field[] value();
    }
    
    @Retention(RUNTIME)
    public @interface Field {

        String value();

        /**
         * Spel expression to determine either value should be selected ignore case or not.
         * May contain spring boot parameter reference.
         * Applicable only for strings. Will be ignored for other types.
         * @return 
         */
        String ignoreCaseExpr() default "true";

    }
            
}
