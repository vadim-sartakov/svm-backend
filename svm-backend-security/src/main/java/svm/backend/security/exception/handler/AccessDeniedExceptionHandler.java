package svm.backend.security.exception.handler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import svm.backend.web.exception.handler.AbstractExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class AccessDeniedExceptionHandler extends AbstractExceptionHandler<AccessDeniedException> {

    public AccessDeniedExceptionHandler(MessageSource messageSource) {
        super(messageSource, AccessDeniedException.class);
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, AccessDeniedException exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.security.AccessDenied");
    }
    
}
