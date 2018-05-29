package svm.backend.security.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import svm.backend.web.exception.handler.AbstractExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class UnauthorizedExceptionHandler extends AbstractExceptionHandler<Throwable> {

    public UnauthorizedExceptionHandler(MessageSource messageSource) {
        super(messageSource, "401");
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.security.Unauthorized");
    }
    
}
