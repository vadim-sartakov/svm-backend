package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class ClientErrorExceptionHandler extends AbstractExceptionHandler<Throwable> {

    public ClientErrorExceptionHandler(MessageSource messageSource) {
        super(messageSource, "4\\d\\d");
    }

    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        logger.warn("Client error occured", exception == null ? null : exception.getMessage());
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.BadRequest");
    }
    
}
