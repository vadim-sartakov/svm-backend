package svm.backend.web.core.exception;

import java.util.Map;
import org.springframework.web.context.request.RequestAttributes;

public interface ExceptionHandler<T extends Throwable> {

    /**
     * Status code for which this handler will be applied to.
     * Has higher priority over isApplicable() method result.
     * @return 
     */
    Integer getStatusCode();
    
    /**
     * Callback method for performing initializing operations.
     * May be used to change status code of the response.
     * @param requestAttributes
     */
    void preHandle(RequestAttributes requestAttributes);
    
    /**
     * Whether this handler is applicable for specified exception type or not.
     * @return 
     */
    Class<T> getExceptionType();
    
    /**
     * Callback method where implementor can modify exception response.
     * @param exceptionAttributes 
     * @param exception 
     */
    void handle(Map<String, Object> exceptionAttributes, T exception);
    
}
