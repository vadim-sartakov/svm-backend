package svm.backend.web.exception;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import svm.backend.web.exception.handler.ExceptionHandler;
import svm.backend.web.exception.handler.ServerErrorExceptionHandler;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private final List<ExceptionHandler<?>> exceptionHandlers;    
        
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        
        Throwable exception = getError(requestAttributes);
        
        Map<String, Object> attributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Object statusValue = attributes.get("status");
        
        int status = -1;
        if (statusValue != null && statusValue instanceof Integer)
            status = (Integer) statusValue;
            
        boolean handled = false;
        for (ExceptionHandler exceptionHandler : exceptionHandlers) {
            
            if (exceptionHandler instanceof ServerErrorExceptionHandler && handled)
                continue;
            
            if (exceptionHandler.isApplicable(exception, status)) {
                
                exceptionHandler.preHandle(requestAttributes);
                attributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
                exceptionHandler.handle(attributes, exception);
                
                handled = true;
                
            }
                
        }
                        
        return attributes;
        
    }
        
}
