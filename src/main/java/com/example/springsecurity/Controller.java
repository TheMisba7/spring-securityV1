package com.example.springsecurity;


import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Controller {


//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

    @GetMapping("/")
    public String hello(){
        return "Welcome to index";
    }

    // Authorized users Only
    @GetMapping("/private")
    public String privatePage(Authentication authentication)
    {
        return "Welcome to VIP room ["+getName(authentication)+"]";
    }
    private String getName(Authentication authentication) {
        return authentication.getName();

}

}
