package svm.backend.web.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import svm.backend.web.exception.handler.ExceptionHandler;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private final List<ExceptionHandler<?>> exceptionHandlers;    
        
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        
        Throwable exception = getError(webRequest);
        
        Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Object statusValue = attributes.get("status");
        
        int status = -1;
        if (statusValue != null && statusValue instanceof Integer)
            status = (Integer) statusValue;
            
        for (ExceptionHandler exceptionHandler : exceptionHandlers) {
            
            if (exceptionHandler.isApplicable(exception, status)) {
                exceptionHandler.preHandle(webRequest);
                attributes = super.getErrorAttributes(webRequest, includeStackTrace);
                exceptionHandler.handle(attributes, exception);
                break;
            }
                
        }
                        
        return attributes;
        
    }
        
}
