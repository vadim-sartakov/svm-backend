package svm.backend.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import svm.backend.security.model.BaseUser;

public class SecurityUtils {
    
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
    
}
