package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import svm.backend.web.core.exception.AbstractByStatusExceptionHandler;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class UnsupportedMediaTypeErrorHandler extends AbstractByStatusExceptionHandler {

    public UnsupportedMediaTypeErrorHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public Integer getStatusCode() {
        return 415;
    }
    
    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.UnsupportedMediaType");
    }
    
}
