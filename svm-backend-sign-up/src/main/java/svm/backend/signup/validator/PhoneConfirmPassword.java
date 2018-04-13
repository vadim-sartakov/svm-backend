package svm.backend.signup.validator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckPhoneConfirmPassword.class)
public @interface PhoneConfirmPassword {
    String message() default "{svm.backend.signup.validator.ConfirmPassword.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };    
}
