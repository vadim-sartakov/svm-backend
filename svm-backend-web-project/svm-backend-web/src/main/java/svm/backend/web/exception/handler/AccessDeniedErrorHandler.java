package svm.backend.web.exception.handler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import svm.backend.web.core.exception.AbstractByTypeExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class AccessDeniedErrorHandler extends AbstractByTypeExceptionHandler<AccessDeniedException> {

    public AccessDeniedErrorHandler(MessageSource messageSource) {
        super(messageSource, AccessDeniedException.class);
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, AccessDeniedException exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.AccessDenied");
    }
    
}
