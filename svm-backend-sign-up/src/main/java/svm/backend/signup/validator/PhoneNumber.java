package svm.backend.signup.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckPhoneNumber.class)
public @interface PhoneNumber {
    String message() default "{svm.backend.dao.entity.useraccount.PhoneNumber.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };    
}

