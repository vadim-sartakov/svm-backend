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
    
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    UniqueValue[] value();
    
    //@Target({ })
    @Retention(RUNTIME)
    public @interface UniqueValue {
        String[] fields();
        String ignoreCaseExpr() default "true";
        String message() default "{svm.backend.signup.validator.UniqueValue.message}";
    }
    
}
