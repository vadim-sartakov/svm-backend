package svm.backend.web.exception.handler.constraint;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class MethodArgumentNotValidExceptionHandler extends ConstraintViolationExceptionHandler<MethodArgumentNotValidException> {

    public MethodArgumentNotValidExceptionHandler(MessageSource messageSource) {
        super(messageSource, MethodArgumentNotValidException.class);
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, MethodArgumentNotValidException exception) {
        super.handle(exceptionAttributes, exception);
        exceptionAttributes.put(ERRORS, fieldErrorsToMap(exception.getBindingResult().getFieldErrors()));
        putConstraintViolationMessage(exceptionAttributes);
    }
    
}
