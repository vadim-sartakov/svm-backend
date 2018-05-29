package svm.backend.web.exception.handler;

import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;

public abstract class AbstractExceptionHandler<T extends Throwable> implements ExceptionHandler<T> {
    
    public static final String MESSAGE = "MESSAGE";
    public static final String ERRORS = "ERRORS";
    
    protected final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    
    private final MessageSource messageSource;
    private Class<T> exceptionType;
    private Pattern statusPattern;
    
    public AbstractExceptionHandler(@Autowired MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public AbstractExceptionHandler(@Autowired MessageSource messageSource, Class<T> exceptionType) {
        this.messageSource = messageSource;
        this.exceptionType = exceptionType;
    }
    
    public AbstractExceptionHandler(@Autowired MessageSource messageSource, String statusRegex) {
        this.messageSource = messageSource;
        this.statusPattern = Pattern.compile(statusRegex);
    }
        
    protected void setStatusCode(RequestAttributes attributes, int status) {
        attributes.setAttribute("javax.servlet.error.status_code", status, 0);
    }

    @Override
    public boolean isApplicable(T exception, int status) {        
        boolean result = exception == null ?
                statusPattern.matcher(Integer.toString(status)).matches() :
                exception.getClass().equals(this.exceptionType);
        logger.debug("Exception handler {} is {} for exception {} and status {}",
                getClass().getCanonicalName(),
                result ? "applicable" : "not applicable",
                exceptionType,
                status);
        return result;
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
