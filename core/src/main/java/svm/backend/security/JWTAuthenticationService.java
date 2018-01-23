/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Вадим
 */
public class JWTAuthenticationService  {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationService.class);
        
    static final long EXPIRATIONTIME = 7200000; // 2 hours
    private static final String SECRET = "bnl+uNsX4rPjvMcoPktaTZJi";
    public static final String COOKIE_NAME = "token";

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> claimsMap;
    
    public void addAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            String id,
            String role) {
            
        this.request = request;
        this.response = response;
        
        claimsMap = new HashMap<>();
        claimsMap.put("sub", id);
        claimsMap.put("role", role);
        claimsMap.put("exp", new Date(System.currentTimeMillis() + EXPIRATIONTIME));
        
        String xsrfToken = UUID.randomUUID().toString();
        claimsMap.put(JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME, xsrfToken);
        
        String JWT = generateToken(claimsMap);
        addCookie(COOKIE_NAME, JWT, true, 7200);
        addCookie(JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME, xsrfToken, false, 0);
        
    }
    
    private void addCookie(String name, String value, boolean httpOnly, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge != 0)
            cookie.setMaxAge(maxAge);
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }

    public String generateToken(Map<String, Object> claims) {
        
        JwtBuilder builder = Jwts.builder();
        claims.entrySet().forEach(claim -> {
            if (claim.getKey().equals("exp"))
                builder.setExpiration((Date) claim.getValue());        
            else
                builder.claim(claim.getKey(), claim.getValue());
        });
            
        return builder
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
                    
    }
    
    public Authentication getAuthentication(HttpServletRequest request) {

        Claims claims = getTokenClaims(request);
        if (claims == null)
            return null;
        
        String userName = claims.getSubject();
        String role = claims.get("role", String.class);

        if (userName == null)
            return null;

        List<GrantedAuthority> roles = new LinkedList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        
        // This has to be entity user
        User authorizedUser = new User(userName, "", roles);
        return new UsernamePasswordAuthenticationToken(authorizedUser , null, roles);

    }
    
    public static Claims getTokenClaims(HttpServletRequest request) {
        
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie == null)
            return null;
        
        String token = cookie.getValue();
                
        Claims claims = null;
        try {
            claims = Jwts.parser()
               .setSigningKey(SECRET)
               .parseClaimsJws(token)
               .getBody();
        } catch (JwtException e) {
            LOGGER.debug(e.getMessage());
        }
        
        return claims;
        
    }
        
}
