package svm.backend.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import svm.backend.web.controller.ApiException;

@JsonSerialize(using = CustomOAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {
        
    private final @Getter ApiException apiException;
    
    public CustomOAuth2Exception(ApiException apiException) {
        super("");
        this.apiException = apiException;
    }
        
}
