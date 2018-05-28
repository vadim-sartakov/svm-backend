package svm.backend.web.exception.handler.constraint;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class MethodArgumentNotValidExceptionHandler extends ConstraintViolationExceptionHandler<MethodArgumentNotValidException> {

    public MethodArgumentNotValidExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, MethodArgumentNotValidException exception) {
        super.handle(exceptionAttributes, exception);
        exceptionAttributes.put(ERRORS, fieldErrorsToMap(exception.getBindingResult().getFieldErrors()));
        putConstraintViolationMessage(exceptionAttributes);
    }
    
}
