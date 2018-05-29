package svm.backend.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

public class ApiErrorWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {
    
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if (e instanceof RuntimeException)
            throw e;
        else
            throw new RuntimeException(e);
    }
        
}
