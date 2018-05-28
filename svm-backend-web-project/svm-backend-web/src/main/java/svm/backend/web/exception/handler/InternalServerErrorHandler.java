package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import svm.backend.web.core.exception.AbstractByStatusExceptionHandler;

@Component
public class InternalServerErrorHandler extends AbstractByStatusExceptionHandler {

    public InternalServerErrorHandler(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public Integer getStatusCode() {
        return 500;
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.InternalServerError");
    }
    
}
