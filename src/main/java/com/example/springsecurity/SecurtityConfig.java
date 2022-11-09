package com.example.springsecurity;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.ArrayList;


@Configuration
@EnableWebSecurity
public class SecurtityConfig {




    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpRequest) throws Exception {

        RobotLoginConfigurer configurer = new RobotLoginConfigurer()
                .addPassword("booss")
                .addPassword("poss");
        return httpRequest
                .authorizeRequests().antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().formLogin(Customizer.withDefaults())
                .httpBasic()
                .and().apply(configurer).and()
                .authenticationProvider(new LazyAdmin())
                .build();

    }

    @Bean
    UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(new User("mansar","{noop}password",new ArrayList<>()));
    }
}
