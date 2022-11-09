package com.example.springsecurity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

//Authentication provider to escape password for user : admin
public class LazyAdmin implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        if("admin".equals(authentication.getPrincipal())){
            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_Admin")
            );
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
