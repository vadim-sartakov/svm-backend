package svm.backend.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired private MessageSource messageSource;
       
    protected ResponseEntity<Object> sendException(String message, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null)
            body = ApiException.builder()
                    .status(status)
                    .message(message)
                    .build();
        return new ResponseEntity<>(body, headers, status);
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return sendException(ex.getMessage(), body, headers, status, request);
    }
    
    private ResponseEntity<Object> handleConstraintViolation(
            Exception ex,
            List<FieldError> fieldErrors,
            WebRequest request) {
        
        String message = messageSource.getMessage("svm.backend.web.WrongObject", null, LocaleContextHolder.getLocale());
        ApiException.ApiExceptionBuilder exceptionBuilder = ApiException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message);
        
        fieldErrors.forEach(fieldError ->
                exceptionBuilder.error(fieldError.getField(), fieldError.getDefaultMessage()));
        
        ApiException exception = exceptionBuilder.build();
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
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleConstraintViolation(ex, ex.getBindingResult().getFieldErrors(), request);
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
