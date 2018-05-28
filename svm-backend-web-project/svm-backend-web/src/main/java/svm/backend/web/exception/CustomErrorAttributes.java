package svm.backend.web.exception;

import svm.backend.web.core.exception.ExceptionHandler;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;

public class CustomErrorAttributes extends DefaultErrorAttributes {

    private final Map<Integer, ExceptionHandler> handlersByStatus;
    private final Map<Class<? extends Throwable>, ExceptionHandler> handlersByClass;
    
    @SuppressWarnings("unchecked")
    public CustomErrorAttributes(@Autowired List<ExceptionHandler<?>> handlers) {
        this.handlersByStatus = handlers.stream()
                .collect(Collectors.toMap(ExceptionHandler::getStatusCode,
                        Function.identity(),
                        (first, second) -> first));
        this.handlersByClass = handlers.stream()
                .collect(Collectors.toMap(ExceptionHandler::getExceptionType, Function.identity(),
                        (first, second) -> first));
    }
    
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        
        Throwable exception = getError(requestAttributes);
        
        Map<String, Object> attributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Object statusValue = attributes.get("status");
        
        int status = -1;
        if (statusValue != null && statusValue instanceof Integer)
            status = (Integer) statusValue;
            
        ExceptionHandler exceptionHandler = null;
        
        if (exception != null)
            exceptionHandler = handlersByClass.get(exception.getClass());
        
        if (exceptionHandler == null && exception != null)
            exceptionHandler = handlersByStatus.get(status);
        
        if (exceptionHandler == null)
            exceptionHandler = handlersByStatus.get(500);
        
        exceptionHandler.preHandle(requestAttributes);
        exceptionHandler.handle(attributes, exception);
                        
        return attributes;
        
    }
        
}
