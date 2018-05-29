package svm.backend.data.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class RepositoryConstraintViolationExceptionHandler extends ConstraintViolationExceptionHandler<RepositoryConstraintViolationException> {

    public RepositoryConstraintViolationExceptionHandler(MessageSource messageSource) {
        super(messageSource, RepositoryConstraintViolationException.class);
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, RepositoryConstraintViolationException exception) {
        super.handle(exceptionAttributes, exception);
        exceptionAttributes.put(ERRORS, fieldErrorsToMap(exception.getErrors().getFieldErrors()));
        putConstraintViolationMessage(exceptionAttributes);
    }
    
}
