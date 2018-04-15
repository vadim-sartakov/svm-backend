package svm.backend.web.controller;

import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import org.springframework.http.HttpStatus;

@Data
@Builder
@RequiredArgsConstructor
public class ApiException {
    
    private final HttpStatus status;
    private final String message;
    
    @Singular
    private final Map<String, String> errors;
 
}
