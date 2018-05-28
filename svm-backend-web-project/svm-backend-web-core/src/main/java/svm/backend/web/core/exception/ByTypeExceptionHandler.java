package svm.backend.web.core.exception;

public interface ByTypeExceptionHandler<T extends Throwable> extends ExceptionHandler<T> {
    /**
     * Whether this handler is applicable for specified exception type or not.
     * Has higher priority over handling by status.
     * @return 
     */
    Class<T> getExceptionType();
    
}
