package svm.backend.security.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import svm.backend.web.exception.handler.AbstractExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class AccessDeniedExceptionHandler extends AbstractExceptionHandler<AccessDeniedException> {

    public AccessDeniedExceptionHandler(MessageSource messageSource) {
        super(messageSource, AccessDeniedException.class);
    }

    @Override
    public void preHandle(RequestAttributes requestAttributes) {
        super.setStatusCode(requestAttributes, 403);
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, AccessDeniedException exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.security.AccessDenied");
    }
    
}
