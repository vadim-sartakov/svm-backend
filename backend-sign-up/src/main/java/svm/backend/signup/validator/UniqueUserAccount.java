package svm.backend.signup.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE, METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckUniqueUsername.class)
public @interface UniqueUserAccount {
    String message() default "{svm.backend.signup.validator.UniqueUserAccount.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
