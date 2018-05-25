package svm.backend.web.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class DefaultException {
    private HttpStatus status;
    private String message;
    private Map<String, String> errors;
}
