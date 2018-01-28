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
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import svm.backend.dao.entity.User;

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
        
    public String generateToken(Authentication authentication, String csrfToken) {
        
        User user = (User) authentication.getPrincipal();
        
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("sub", user.getUsername());
        claimsMap.put("roles", user.getAuthorities());
        claimsMap.put("exp", new Date(System.currentTimeMillis() + expirationTime));
        claimsMap.put(JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME, csrfToken);
        
        return generateToken(claimsMap);
                
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
        
    public Claims getTokenClaims(String token) {
                
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
