package svm.backend.security.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import svm.backend.web.exception.handler.AbstractExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class InsufficientAuthorizationExceptionHandler extends AbstractExceptionHandler<InsufficientAuthenticationException> {

    public InsufficientAuthorizationExceptionHandler(MessageSource messageSource) {
        super(messageSource, InsufficientAuthenticationException.class);
    }

    @Override
    public void preHandle(RequestAttributes requestAttributes) {
        super.setStatusCode(requestAttributes, 401);
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, InsufficientAuthenticationException exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.security.Unauthorized");
    }
    
}
