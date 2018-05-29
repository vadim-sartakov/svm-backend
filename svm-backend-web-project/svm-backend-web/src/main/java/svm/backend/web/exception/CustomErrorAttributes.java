package svm.backend.web.exception;

import svm.backend.web.core.exception.ExceptionHandler;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import svm.backend.web.core.exception.ByStatusExceptionHandler;
import svm.backend.web.core.exception.ByTypeExceptionHandler;

public class CustomErrorAttributes extends DefaultErrorAttributes {

    private final Map<Class<? extends Throwable>, ByTypeExceptionHandler<?>> byClassHandlers;
    private final Map<Integer, ByStatusExceptionHandler> byStatusHandlers;    
    
    @Autowired
    public CustomErrorAttributes(List<ByTypeExceptionHandler<?>> byClassHandlers,
            List<ByStatusExceptionHandler> byStatusHandlers) {
        this.byClassHandlers = byClassHandlers.stream()
                .collect(Collectors.toMap(ByTypeExceptionHandler::getExceptionType, Function.identity(),
                        (first, second) -> second));
        this.byStatusHandlers = byStatusHandlers.stream()
                .collect(Collectors.toMap(ByStatusExceptionHandler::getStatusCode,
                        Function.identity(),
                        (first, second) -> second));
    }
    
    @SuppressWarnings("unchecked")
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
            exceptionHandler = byClassHandlers.get(exception.getClass());
        
        if (exceptionHandler == null)
            exceptionHandler = byStatusHandlers.get(status);
        
        if (exceptionHandler == null)
            exceptionHandler = byStatusHandlers.get(500);
        
        exceptionHandler.preHandle(requestAttributes);
        exceptionHandler.handle(attributes, exception);
                        
        return attributes;
        
    }
        
}
