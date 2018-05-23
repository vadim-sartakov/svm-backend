package svm.backend.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import svm.backend.security.model.BaseUser;

public class SecurityUtils {
    
    private static Authentication previousAuthentication;
    
    /**
     * Places specified user in the security context.
     * @param <T>
     * @param user 
     */
    public static <T extends BaseUser> void setAuthentication(T user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
    }
    
    /**
     * Restores previous authentication in security context.
     * Spring docs states that context created per thread, so it's supposed to be
     * thread safe.
     */
    public static void restoreAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(previousAuthentication);
        previousAuthentication = null;
    }
    
}
