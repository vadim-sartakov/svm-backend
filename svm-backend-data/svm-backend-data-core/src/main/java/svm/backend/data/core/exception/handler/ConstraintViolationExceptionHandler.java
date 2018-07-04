package svm.backend.data.core.exception.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestAttributes;
import svm.backend.web.exception.handler.AbstractExceptionHandler;

public abstract class ConstraintViolationExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> {

    public ConstraintViolationExceptionHandler(MessageSource messageSource, Class<T> exceptionType) {
        super(messageSource, exceptionType);
    }
        
    protected Map<String, String> fieldErrorsToMap(List<FieldError> fieldErrors) {
        Map<String, String> errorsMap = new HashMap<>();
        fieldErrors.forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorsMap;
    }
    
    protected void putConstraintViolationMessage(Map<String, Object> exceptionAttributes) {
        putMessage(exceptionAttributes, "svm.backend.data.WrongObject");
    }

    @Override
    public void preHandle(RequestAttributes requestAttributes) {
        super.setStatusCode(requestAttributes, 400);
    }
    
}
