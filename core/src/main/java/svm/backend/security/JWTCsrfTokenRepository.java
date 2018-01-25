package svm.backend.security;

import io.jsonwebtoken.Claims;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * Token generated and saved by TokenAuthenticationService
 * @author Sartakov
 */
public class JWTCsrfTokenRepository implements CsrfTokenRepository {

    public static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";
    public static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    public static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    private final String parameterName = DEFAULT_CSRF_PARAMETER_NAME;
    private final String headerName = DEFAULT_CSRF_HEADER_NAME;
    private final String cookieName = DEFAULT_CSRF_COOKIE_NAME;
    private final CsrfToken dummyToken = new DefaultCsrfToken(
            headerName,
            parameterName,
            cookieName
    );
    
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return dummyToken;
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) { 
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        Claims claims = JWTAuthenticationService.getTokenClaims(request);
        if (claims == null)
            return null;
        return new DefaultCsrfToken(this.headerName, this.parameterName, claims.get(cookieName, String.class));
    }
     
}
