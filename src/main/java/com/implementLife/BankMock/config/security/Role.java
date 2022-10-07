package com.implementLife.BankMock.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    NONE('N', "ROLE_NONE"),
    USER('U', "ROLE_USER"),
    ADMIN('A', "ROLE_ADMIN");

    private final char id;
    private final String name;

    Role(char id, String name) {
        this.id = id;
        this.name = name;
    }

    public char getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public static Role getById(char id) {
        if (NONE.id == id) return NONE;
        if (USER.id == id) return USER;
        if (ADMIN.id == id) return ADMIN;
        throw new IllegalArgumentException("Not exist role with id=" + id);
    }
}
