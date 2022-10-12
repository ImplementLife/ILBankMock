package com.implementLife.BankMock.config.security;

import com.implementLife.BankMock.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClientSec implements UserDetails {
    private final Client client;
    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
        fillRolesIdInClient();
    }
    public void addRole(Role role) {
        this.roles.add(role);
        fillRolesIdInClient();
    }

    private void fillRole() {
        if (this.roles != null) {
            this.roles = new HashSet<>(this.roles);
        } else {
            this.roles = new HashSet<>();
        }
        String idRoles = client.getRoles();
        if (idRoles != null) {
            for (char id : idRoles.toCharArray()) {
                this.roles.add(Role.getById(id));
            }
        }
    }

    private void fillRolesIdInClient() {
        StringBuilder rolesIdStringBuilder = new StringBuilder();
        for (Role role : this.roles) {
            rolesIdStringBuilder.append(role.getId());
        }
        client.setRoles(rolesIdStringBuilder.toString());
    }

    public ClientSec(Client client) {
        this.client = client;
        fillRole();
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
