package svm.backend.web.exception;

import svm.backend.web.core.exception.ExceptionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;

public class CustomErrorAttributes extends DefaultErrorAttributes {
    
    private final static String ERROR = "ERROR";
    
    @Autowired private MessageSource messageSource;
    private Map<Class<? extends Throwable>, ExceptionHandler> handlers = new HashMap<>();
    
    private Map<String, Object> attributes;
    private Throwable exception;
    private int status;
    
    @Autowired(required = false)
    public void setHandlers(Set<ExceptionHandler> handlers) {        
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(ExceptionHandler::getExceptionClass, Function.identity()));
    }
    
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        
        this.exception = getError(requestAttributes);
        
        attributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Object statusValue = attributes.get("status");
        if (statusValue != null && statusValue instanceof Integer)
            status = (Integer) statusValue;
            
        ExceptionHandler<?> handler = handlers.get(exception.getClass());
        if (handler == null)
            handle();
        else
            handler.handle(attributes);
        
        return attributes;
        
    }
    
    protected void handle() {
        
        switch(status) {
            case 400:
                handleWrongRequest();
            case 401:
                handleUnauthorized();
            case 403:
                putMessage("svm.backend.security.AccessDenied");
            case 404:
                putMessage("svm.backend.web.NotFound");
            case 405:
                putMessage("svm.backend.web.MethodNotAllowed");
            default:
                putMessage("svm.backend.web.InternalServerError");
        }
        
    }
    
    private void handleWrongRequest() {
        
        if (exception instanceof RepositoryConstraintViolationException) {
            
            RepositoryConstraintViolationException repositoryException =
                    (RepositoryConstraintViolationException) exception;
            attributes.put(ERROR, fieldErrorsToMap(repositoryException.getErrors().getFieldErrors()));
            putMessage("svm.backend.web.WrongObject");
            
        } else if (exception instanceof BindException) {
            
            BindException bindException = (BindException) exception;
            attributes.put(ERROR, fieldErrorsToMap(bindException.getFieldErrors()));
            
        } else if (exception instanceof MethodArgumentNotValidException) {
            
            MethodArgumentNotValidException methodArgException = (MethodArgumentNotValidException) exception;
            attributes.put(ERROR, fieldErrorsToMap(methodArgException.getBindingResult().getFieldErrors()));
            
        }
        
    }
    
    private Map<String, String> fieldErrorsToMap(List<FieldError> fieldErrors) {
        Map<String, String> errorsMap = new HashMap<>();
        fieldErrors.forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errorsMap;
    }
    
    private void handleUnauthorized() {
        
        /*if (exception instanceof BadCredentialsException)
            putMessage("svm.backend.security.BadCredentials");
        else if (exception instanceof BadClientCredentialsException)
            putMessage("svm.backend.security.BadClientCredentials");
        else*/
            putMessage("svm.backend.security.Unauthorized");
        
    }
    
    private void putMessage(String code) {
        attributes.put("message", messageSource.getMessage(
                code,
                null,
                LocaleContextHolder.getLocale())
        );
    }
        
}
