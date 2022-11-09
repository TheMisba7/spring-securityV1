package com.example.springsecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RobotLoginConfigurer extends AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity> {
    private final List<String> passwords = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) throws Exception {
        //Create Robot authentication provider: so we can test password or username...
        http.authenticationProvider(new RobotAuthenticationProvider(passwords));

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // Create Robot filter and add it to filter chain
        AuthenticationManager authManager =  http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new RobotFilter(authManager), UsernamePasswordAuthenticationFilter.class);
    }

    public RobotLoginConfigurer addPassword(String password)
    {
        this.passwords.add(password);
        return this;
    }
}
