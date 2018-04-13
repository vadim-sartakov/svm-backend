package svm.backend.security.filter;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;
import svm.backend.security.dao.entity.User;
import svm.backend.security.service.JWTService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    
    @Autowired private JWTService jwtService;
    
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                
        Claims claims = getTokenClaims(request);

        // If authentication is already set (by test environment), then skip authentication setting)
        if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           
            String commaSeparetedRoles = claims.get("roles", String.class);
            List<GrantedAuthority> roles = new LinkedList<>();
            
            if (!commaSeparetedRoles.isEmpty())
                for (String role : commaSeparetedRoles.split(","))                
                    roles.add(new SimpleGrantedAuthority("ROLE_" + role));

            User user = new User();
            user.setId(UUID.fromString(claims.getSubject()));
            user.setAuthorities(roles);
            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    roles
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        }

        chain.doFilter(request, response);
    }

    public Claims getTokenClaims(HttpServletRequest request) {
        
        Cookie cookie = WebUtils.getCookie(request, JWTService.COOKIE_NAME);
        if (cookie == null)
            return null;

        return jwtService.getTokenClaims(cookie.getValue());
                
    }
    
}
