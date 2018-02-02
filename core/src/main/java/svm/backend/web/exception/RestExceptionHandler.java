package svm.backend.web.exception;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException apiException = new ApiException(status, ex.getMessage());
        return new ResponseEntity<>(apiException, headers, status);
    }

    protected List<FieldError> constraintViolationsToFieldErrors(ConstraintViolationException violations) {
                    
        List<FieldError> fieldErrors = violations.getConstraintViolations()
                .stream()
                .map(violation -> new FieldError(
                        violation.getRootBeanClass().getSimpleName(),
                        violation.getPropertyPath().toString(),
                        violation.getMessage())
                )
                .collect(Collectors.toList());

        return fieldErrors;
                
    }
    
    private ResponseEntity<Object> handleConstrainViolation(
            Exception ex,
            List<FieldError> fieldErrors,
            WebRequest request) {
        
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, "Неверный объект");
        
        fieldErrors.forEach(fieldError ->
                exception.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
        
    }
    
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<Object> handleRepositoryConstraintViolation(
            RepositoryConstraintViolationException ex,
            WebRequest request) {
        return handleConstrainViolation(ex, ex.getErrors().getFieldErrors(), request);        
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error(ex.getLocalizedMessage(), ex);
        ApiException exception = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера");
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }
        
}
