package svm.backend.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ApiException {
    
    private final HttpStatus status;
    private final String message;
    private final Map<String, String> errors = new HashMap<>();
 
    public ApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public void addError(String field, String error) {
        errors.put(field, error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
    
}
