package svm.backend.security.exception;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired private MessageSource messageSource;
       
    protected ResponseEntity<Object> sendException(String message, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null)
            body = new ApiException(status, message);
        return new ResponseEntity<>(body, headers, status);
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return sendException(ex.getMessage(), body, headers, status, request);
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
    
    private ResponseEntity<Object> handleConstraintViolation(
            Exception ex,
            List<FieldError> fieldErrors,
            WebRequest request) {
        
        String message = messageSource.getMessage("svm.backend.web.WrongObject", null, LocaleContextHolder.getLocale());
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, message);
        
        fieldErrors.forEach(fieldError ->
                exception.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
        
    }
    
    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<Object> handleRepositoryConstraintViolation(
            RepositoryConstraintViolationException ex,
            WebRequest request) {
        return handleConstraintViolation(ex, ex.getErrors().getFieldErrors(), request);        
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleConstraintViolation(ex, ex.getFieldErrors(), request);
    }

    /**
     * Thrown by hibernate during entity persisting.
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<FieldError> fieldErrors = constraintViolationsToFieldErrors(ex);
        return handleConstraintViolation(ex, fieldErrors, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = messageSource.getMessage("svm.backend.web.NotFound", null, LocaleContextHolder.getLocale());
        return sendException(message, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleConstraintViolation(ex, ex.getBindingResult().getFieldErrors(), request);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(Exception ex, WebRequest request) {
        String message = messageSource.getMessage("AbstractAccessDecisionManager.accessDenied", null, LocaleContextHolder.getLocale());
        return sendException(message, null, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
    
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    /**
     * In none exception has been intercepted - throw internal server error.
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error(ex.getLocalizedMessage(), ex);
        String message = messageSource.getMessage("svm.backend.web.InternalError", null, LocaleContextHolder.getLocale());
        return sendException(message, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
        
}
