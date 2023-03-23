package com.impllife.bankmock.config.security;

import com.impllife.bankmock.data.entity.security.ClientSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private SecurityClientInfo securityClientInfo;
    @Autowired
    private TwoFactorAuthService twoFactorAuthService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();


        ClientSec clientSec;
        try {
            clientSec = securityClientInfo.loadClient(name);
        } catch (Exception e) {
            throw new BadCredentialsException("User not exist");
        }

        if (!passwordEncoder.matches(password, clientSec.getPassword())) {
            throw new BadCredentialsException("Passwords doe's not matched");
        }

        twoFactorAuthService.auth(clientSec);

        return new UsernamePasswordAuthenticationToken(clientSec, password, clientSec.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}