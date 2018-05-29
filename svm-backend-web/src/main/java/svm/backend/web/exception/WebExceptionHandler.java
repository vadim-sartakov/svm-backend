package svm.backend.web.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Intercepts exceptions thrown by controllers and throws it further.
 * This approach sends all exceptions to ErrorController.
 * @author Sartakov
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof RuntimeException)
            throw (RuntimeException) ex;
        else
            throw new RuntimeException(ex);
    }    
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex, HttpServletRequest request) throws Exception {
        throw ex;
    }
        
}
