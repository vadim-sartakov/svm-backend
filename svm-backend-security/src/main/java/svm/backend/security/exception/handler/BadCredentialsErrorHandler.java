package svm.backend.security.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import svm.backend.web.exception.handler.AbstractByTypeExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class BadCredentialsErrorHandler extends AbstractByTypeExceptionHandler<BadCredentialsException> {

    public BadCredentialsErrorHandler(MessageSource messageSource) {
        super(messageSource, BadCredentialsException.class);
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, BadCredentialsException exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.security.BadCredentials");
    }
    
}
