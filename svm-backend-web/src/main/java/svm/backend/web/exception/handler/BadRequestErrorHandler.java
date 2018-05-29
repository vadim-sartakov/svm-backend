package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class BadRequestErrorHandler extends AbstractByStatusExceptionHandler {

    public BadRequestErrorHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.BadRequest");
    }
    
}
