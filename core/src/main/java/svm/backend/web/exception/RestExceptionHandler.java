package svm.backend.web.exception;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    private Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleWrongCredentials(BadCredentialsException ex,
            WebRequest request) {
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }
    
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex,
            WebRequest request) {
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }
    
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleBadRequest(NotFoundException ex,
            WebRequest request) {
        ApiException exception = new ApiException(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleBadRequest(AccessDeniedException ex,
            WebRequest request) {
        ApiException exception = new ApiException(HttpStatus.FORBIDDEN, ex.getMessage());
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        Throwable cause = ex.getMostSpecificCause();
        if (cause instanceof ConstraintViolationException) {
            
            ConstraintViolationException violations = (ConstraintViolationException) cause;
            List<FieldError> fieldErrors = violations.getConstraintViolations()
                    .stream()
                    .map(violation -> new FieldError(
                            violation.getRootBeanClass().getSimpleName(),
                            violation.getPropertyPath().toString(),
                            violation.getMessage())
                    )
                    .collect(Collectors.toList());
            
            return handleConstrainViolation(ex, fieldErrors, request);
            
        } else {
            ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, ex.getMessage());
            return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
        }
                
    }
        
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleConstrainViolation(ex, ex.getBindingResult().getFieldErrors(), request);        
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
    protected ResponseEntity<Object> handleRepositoryConstraintViolation(
            RepositoryConstraintViolationException ex,
            WebRequest request) {
        return handleConstrainViolation(ex, ex.getErrors().getFieldErrors(), request);        
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException exception = new ApiException(HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException exception = new ApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        LOGGER.error(ex.getLocalizedMessage(), ex);
        ApiException exception = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера");
        return handleExceptionInternal(ex, exception, new HttpHeaders(), exception.getStatus(), request);
    }
        
}
