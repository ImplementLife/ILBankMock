package com.implementLife.BankMock.config.security;

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

        ClientSec clientSec = securityClientInfo.loadClient(name);
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