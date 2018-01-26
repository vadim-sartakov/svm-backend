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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class JWTService  {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTService.class);
    public static final String COOKIE_NAME = "token";
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expirationTime}")
    private long expirationTime;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> claimsMap;
        
    public String generateToken(Authentication authentication, String csrfToken) {
        
        Map<String, Object> claims = new HashMap<>();
        claimsMap.put("sub", authentication.getPrincipal().toString());
        claimsMap.put("roles", authentication.getAuthorities());
        claimsMap.put("exp", new Date(System.currentTimeMillis() + expirationTime));
        claimsMap.put(JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME, csrfToken);
        
        return generateToken(claims);
                
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
                .signWith(SignatureAlgorithm.HS512, secret)
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
    
    public Claims getTokenClaims(HttpServletRequest request) {
        
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
        if (cookie == null)
            return null;
        
        String token = cookie.getValue();
                
        Claims claims = null;
        try {
            claims = Jwts.parser()
               .setSigningKey(secret)
               .parseClaimsJws(token)
               .getBody();
        } catch (JwtException e) {
            LOGGER.debug(e.getMessage());
        }
        
        return claims;
        
    }
        
}
