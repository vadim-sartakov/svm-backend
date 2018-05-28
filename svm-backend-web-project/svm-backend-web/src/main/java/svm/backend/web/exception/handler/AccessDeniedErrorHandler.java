package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import svm.backend.web.core.exception.AbstractByStatusExceptionHandler;

@Component
public class AccessDeniedErrorHandler extends AbstractByStatusExceptionHandler {

    public AccessDeniedErrorHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public Integer getStatusCode() {
        return 403;
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.AccessDenied");
    }
    
}
