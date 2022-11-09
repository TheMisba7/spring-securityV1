package com.example.springsecurity;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public RobotFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // should we execute the filter?? YES if the header contains : robot-login
        // if not so we call the filter chain to go to the next filter
        if(!Collections.list(request.getHeaderNames()).contains("robot-login"))
        {
            filterChain.doFilter(request,response);
            return;
        }

        //Authentication Decision
        String password = request.getHeader("robot-login");
        RobotAuthentication authRequest = RobotAuthentication.unAuthenticated(password);


        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authRequest);
            //set authentication to security context holder
            SecurityContext newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request,response);
            return;
        } catch (AuthenticationException e) {
            // password wrong go away!!
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type","text/plain;charset=utf-8");
            response.getWriter().println(e.getMessage());
        }





    }
}
