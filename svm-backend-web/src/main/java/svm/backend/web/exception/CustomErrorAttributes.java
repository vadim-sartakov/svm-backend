package svm.backend.web.exception;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;

public class CustomErrorAttributes extends DefaultErrorAttributes {
    
    @Autowired private MessageSource messageSource;
    
    private Map<String, Object> attributes;
    private int status;
    
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        
        attributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Object statusValue = attributes.get("status");
        if (statusValue != null && statusValue instanceof Integer)
            status = (Integer) statusValue;
        
        setLocalizedMessage();
        
        return attributes;
        
    }
    
    protected void setLocalizedMessage() {
        
        switch(status) {
            case 401:
                putMessage("svm.backend.security.Unauthorized");
                break;
            case 403:
                putMessage("svm.backend.security.AccessDenied");
                break;
            case 404:
                putMessage("svm.backend.web.NotFound");
                break;
            case 405:
                putMessage("svm.backend.web.MethodNotAllowed");
                break;
            default:
        }
                
    }
    
    private void putMessage(String code) {
        attributes.put("message", messageSource.getMessage(
                code,
                null,
                LocaleContextHolder.getLocale())
        );
    }
    
}
