package com.impllife.bankmock.data.entity.security;

import com.impllife.bankmock.data.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ClientSec implements UserDetails {
    private final Client client;
    private Set<Role> roles;
    private String rolesAsString;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
        fillRolesStringClient();
    }
    public void addRole(Role role) {
        this.roles.add(role);
        fillRolesStringClient();
    }

    private void fillRoles() {
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

    private void fillRolesStringClient() {
        StringBuilder rolesIdStringBuilder = new StringBuilder();
        for (Role role : this.roles) {
            rolesIdStringBuilder.append(role.getId());
        }
        client.setRoles(rolesIdStringBuilder.toString());
    }

    public ClientSec(Client client) {
        this.client = client;
        this.rolesAsString = client.getRoles();
        fillRoles();
    }

    public Client getClient() {
        return client;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!Objects.equals(this.rolesAsString, client.getRoles())) {
            fillRoles();
        }
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
