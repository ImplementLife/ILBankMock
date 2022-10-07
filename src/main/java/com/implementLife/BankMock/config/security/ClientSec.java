package com.implementLife.BankMock.config.security;

import com.implementLife.BankMock.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ClientSec implements UserDetails {
    private Client client;
    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public ClientSec(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return client.getPass();
    }

    @Override
    public String getUsername() {
        String email = client.getEmail();
        if (email != null && !email.equals("")) {
            return email;
        } else {
            return client.getId().toString();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
