package ru.laboratory.blps.auth;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    user,
    admin;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
