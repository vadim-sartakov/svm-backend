package svm.backend.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
import svm.backend.security.JWTService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    
    @Autowired private JWTService jwtService;
    
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                
        Claims claims = getTokenClaims(request);

        if (claims == null) {
            chain.doFilter(request, response);
            return;
        }

        String commaSeparetedRoles = claims.get("roles", String.class);
        
        List<GrantedAuthority> roles = new LinkedList<>();
        for (String role : commaSeparetedRoles.split(","))
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                // Put user here
                claims.getSubject(),
                null,
                roles
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    public Claims getTokenClaims(HttpServletRequest request) {
        
        Cookie cookie = WebUtils.getCookie(request, JWTService.COOKIE_NAME);
        if (cookie == null)
            return null;

        return jwtService.getTokenClaims(cookie.getValue());
                
    }
    
}
