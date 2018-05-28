package svm.backend.web.exception.handler.constraint;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class BindExceptionHandler extends ConstraintViolationExceptionHandler<BindException> {

    public BindExceptionHandler(MessageSource messageSource) {
        super(messageSource, BindException.class);
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, BindException exception) {
        super.handle(exceptionAttributes, exception);
        exceptionAttributes.put(ERRORS, fieldErrorsToMap(exception.getFieldErrors()));
        putConstraintViolationMessage(exceptionAttributes);
    }
    
}
