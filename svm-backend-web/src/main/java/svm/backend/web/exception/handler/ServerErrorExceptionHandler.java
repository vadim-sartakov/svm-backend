package svm.backend.web.exception.handler;

import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ServerErrorExceptionHandler extends AbstractExceptionHandler<Throwable> {

    public ServerErrorExceptionHandler(MessageSource messageSource) {
        super(messageSource);
    }

    /**
     * Always applicable as it is default one.
     * @param exception
     * @param status
     * @return 
     */
    @Override
    public boolean isApplicable(Throwable exception, int status) {
        return true;
    }
 

    @Override
    public void handle(Map<String, Object> exceptionAttributes, Throwable exception) {
        logger.error("Server error occured", exception);
        super.handle(exceptionAttributes, exception);
        putMessage(exceptionAttributes, "svm.backend.web.InternalServerError");
    }
    
}
