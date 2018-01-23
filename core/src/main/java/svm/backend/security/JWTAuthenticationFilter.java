/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm.backend.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author Вадим
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    @Autowired private JWTAuthenticationService tokenAuthenticationService;
    
    @Override
    public void doFilter(ServletRequest request,
                ServletResponse response,
                FilterChain filterChain) throws IOException, ServletException {
        
        // Skip if authentication is already set (for example, by test)
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication authentication = tokenAuthenticationService
                    .getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
        
    }
    
}
