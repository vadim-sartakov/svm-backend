package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.web.context.request.RequestAttributes;

public interface ExceptionHandler<T extends Throwable> {
    
    /**
     * Callback method for performing initializing operations.
     * May be used to change status code of the response.
     * @param requestAttributes
     */
    void preHandle(RequestAttributes requestAttributes);
    
    /**
     * Callback method where implementor can modify exception response.
     * @param exceptionAttributes 
     * @param exception 
     */
    void handle(Map<String, Object> exceptionAttributes, T exception);
            
}
