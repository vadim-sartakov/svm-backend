/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.web.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 *
 * @author sartakov
 */
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
