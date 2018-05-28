package svm.backend.web.core.exception;

import org.springframework.context.MessageSource;

public abstract class AbstractByStatusExceptionHandler extends AbstractExceptionHandler<Throwable> implements ByStatusExceptionHandler {
        
    public AbstractByStatusExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }
        
}
