package svm.backend.web.core.exception;

public interface ByStatusExceptionHandler extends ExceptionHandler<Throwable> {
    
    /**
     * Status code for which this handler will be applied to.
     * @return 
     */
    Integer getStatusCode();
        
}
