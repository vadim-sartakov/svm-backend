package svm.backend.security;

import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

public class JWTCsrfTokenRepository implements CsrfTokenRepository {

    public static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";
    public static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    public static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;
    private String headerName = DEFAULT_CSRF_HEADER_NAME;
    private String cookieName = DEFAULT_CSRF_COOKIE_NAME;
    
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(
                headerName,
                parameterName,
                UUID.randomUUID().toString()
        );
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader(headerName, token.getToken());       
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        
        String token = request.getHeader(headerName);
        if (token == null)
            return null;
                
        return new DefaultCsrfToken(
                headerName,
                "parameterName",
                token
        );
        
    }
    
}
