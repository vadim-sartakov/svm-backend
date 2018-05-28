package svm.backend.web.exception.handler.constraint;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
public class RepositoryConstraintViolationExceptionHandler extends ConstraintViolationExceptionHandler<RepositoryConstraintViolationException> {

    public RepositoryConstraintViolationExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, RepositoryConstraintViolationException exception) {
        super.handle(exceptionAttributes, exception);
        exceptionAttributes.put(ERRORS, fieldErrorsToMap(exception.getErrors().getFieldErrors()));
        putConstraintViolationMessage(exceptionAttributes);
    }
    
}
