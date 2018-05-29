package svm.backend.web.exception.handler;

import org.springframework.context.MessageSource;

public abstract class AbstractByTypeExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> implements ByTypeExceptionHandler<T> {
    
    private final Class<T> exceptionType;

    public AbstractByTypeExceptionHandler(MessageSource messageSource, Class<T> exceptionType) {
        super(messageSource);
        this.exceptionType = exceptionType;
    }

    @Override
    public Class<T> getExceptionType() {
        return exceptionType;
    }
    
}
