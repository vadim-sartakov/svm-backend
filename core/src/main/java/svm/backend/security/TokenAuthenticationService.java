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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Вадим
 */
public class TokenAuthenticationService  {
        
    private final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);
        
    static final long EXPIRATIONTIME = 7200000; // 2 hours
    static final String SECRET = "bnl+uNsX4rPjvMcoPktaTZJi";
    static final String COOKIE_NAME = "token";

    public void addAuthentication(
            HttpServletResponse res,
            String id,
            String role) {
            
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", id);
        claims.put("role", role);
        claims.put("exp", new Date(System.currentTimeMillis() + EXPIRATIONTIME));
        
        String JWT = generateToken(claims);        
        Cookie cookie = new Cookie(COOKIE_NAME, JWT);
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        //cookie.setSecure(true);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
        
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
        
        String token = null;
        Cookie[] cookies = request.getCookies();
        
        if (cookies == null)
            return null;
        
        for (Cookie cookie : cookies) {
            
            if (cookie.getName().equals(COOKIE_NAME)) {
                token = cookie.getValue();
            }
                          
        }
        
        if (token == null)
            return null;
          
        Claims claims;
        try {
            claims = Jwts.parser()
               .setSigningKey(SECRET)
               .parseClaimsJws(token)
               .getBody();
        } catch (JwtException e) {
            logger.debug(e.getMessage());
            return null;
        }

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
    
}
