package svm.backend.web.core.exception;

import java.util.Map;

public interface ExceptionHandler<T extends Throwable> {

    Class<T> getExceptionClass();
    
    /**
     * Callback method where implementor can modify exception response.
     * @param exceptionAttributes 
     */
    void handle(Map<String, Object> exceptionAttributes);
    
}
