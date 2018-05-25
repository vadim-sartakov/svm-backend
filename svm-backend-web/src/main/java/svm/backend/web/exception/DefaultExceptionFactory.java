package svm.backend.web.exception;

import java.util.HashMap;
import svm.backend.core.service.ExceptionFactory;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class DefaultExceptionFactory implements ExceptionFactory<DefaultException> {

    @Override
    public DefaultException createException(String code, String message) {
        return new DefaultException(HttpStatus.valueOf(Integer.parseInt(code)), message, new HashMap<>());
    }

    @Override
    public DefaultException createDetailedException(String code, String message, Map<String, String> details) {
        return new DefaultException(HttpStatus.valueOf(Integer.parseInt(code)), message, details);
    }
        
}
