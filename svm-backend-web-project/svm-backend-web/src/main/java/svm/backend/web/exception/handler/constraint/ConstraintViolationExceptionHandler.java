package svm.backend.web.exception.handler.constraint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import svm.backend.web.core.exception.AbstractByTypeExceptionHandler;

public abstract class ConstraintViolationExceptionHandler<T extends Throwable> extends AbstractByTypeExceptionHandler<T> {

    public ConstraintViolationExceptionHandler(MessageSource messageSource, Class<T> exceptionType) {
        super(messageSource, exceptionType);
    }
        
    protected Map<String, String> fieldErrorsToMap(List<FieldError> fieldErrors) {
        Map<String, String> errorsMap = new HashMap<>();
        fieldErrors.forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorsMap;
    }
    
    protected void putConstraintViolationMessage(Map<String, Object> exceptionAttributes) {
        putMessage(exceptionAttributes, "svm.backend.web.WrongObject");
    }
    
}
