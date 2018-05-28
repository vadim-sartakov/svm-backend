package svm.backend.web.core.exception;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;

public abstract class AbstractExceptionHandler<T extends Throwable> implements ExceptionHandler<T> {
    
    public static final String MESSAGE = "MESSAGE";
    public static final String ERRORS = "ERRORS";
    
    private final MessageSource messageSource;
    private final Class<T> exceptionType;
    
    public AbstractExceptionHandler(@Autowired MessageSource messageSource) {
        this(messageSource, null);
    }
    
    public AbstractExceptionHandler(@Autowired MessageSource messageSource, Class<T> exceptionType) {
        this.messageSource = messageSource;
        this.exceptionType = exceptionType;
    }
    
    @Override
    public Integer getStatusCode() {
        return null;
    }
    
    protected void setStatusCode(RequestAttributes attributes, int status) {
        attributes.setAttribute("javax.servlet.error.status_code", status, 0);
    }

    @Override
    public Class<T> getExceptionType() {
        return exceptionType;
    }

    @Override
    public void preHandle(RequestAttributes requestAttributes) { }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, T exception) {
        exceptionAttributes.remove("error");
        exceptionAttributes.remove("exception");
    }
    
    protected void putMessage(Map<String, Object> attributes, String code) {
        attributes.put("message", messageSource.getMessage(
                code,
                null,
                LocaleContextHolder.getLocale())
        );
    }

}
