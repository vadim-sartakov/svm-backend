package svm.backend.security.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import svm.backend.web.controller.ApiException;

public class ApiErrorWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Autowired private MessageSource messageSource;
    
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        
        ResponseEntity<OAuth2Exception> defaultResponse = super.translate(e);        
        String messageCode = getMessageCode(defaultResponse.getBody());
        
        ApiException apiException = ApiException.builder()
                .message(messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()))
                .status(defaultResponse.getStatusCode())
                .build();        
        
        CustomOAuth2Exception exception = new CustomOAuth2Exception(apiException);
        return new ResponseEntity<>(exception, defaultResponse.getHeaders(), exception.getApiException().getStatus());
        
    }
    
    private String getMessageCode(OAuth2Exception exception) {
        
        switch(exception.getOAuth2ErrorCode()) {
            case "unauthorized":
                return "svm.backend.security.Unauthorized";
            case "access_denied":
                return "svm.backend.security.AccessDenied";
            case "invalid_client":
                return "svm.backend.security.BadCredentials";
            default:
                return "empty";
        }
        
    }
    
}
