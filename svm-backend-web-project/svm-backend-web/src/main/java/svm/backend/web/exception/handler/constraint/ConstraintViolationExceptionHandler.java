package svm.backend.web.exception.handler.constraint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import svm.backend.web.core.exception.AbstractExceptionHandler;

public abstract class ConstraintViolationExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> {

    public ConstraintViolationExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
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
