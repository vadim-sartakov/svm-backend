package svm.backend.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CustomOAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {
        
    private final @Getter Object exception;
    
    public CustomOAuth2Exception(Object exception) {
        super("");
        this.exception = exception;
    }
        
}
