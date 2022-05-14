package ru.laboratory.blps.configuration.security.providers;

import ru.laboratory.blps.model.user.Credentials;
import ru.laboratory.blps.model.user.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final User principal;
    private final Credentials credentials;

    public JWTAuthenticationToken(User principal, Credentials credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
